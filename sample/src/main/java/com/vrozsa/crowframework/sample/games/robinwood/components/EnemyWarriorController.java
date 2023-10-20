package com.vrozsa.crowframework.sample.games.robinwood.components;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.shared.api.game.GameCommand;
import com.vrozsa.crowframework.shared.api.game.PositionComponent;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.time.Cooldown;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_DOWN;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_LEFT;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_RIGHT;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_UP;
import static java.util.Objects.isNull;

public class EnemyWarriorController extends AbstractComponent {
    private static final int TARGET_CALC_MIN_COOLDOWN = 500;
    private static final int TARGET_CALC_MAX_COOLDOWN = 1000;
    private static final int SPEED_BOOST_TARGET_DISTANCE = 400;
    private static final int AXIS_SPEED_B0OST = 5;
    private static final int DIAGONAL_SPEED_B0OST = 4;
    private static final int MIN_BOOSTING_TIME = 200;
    private static final Offset MIN_ATTACK_DIST_OFFSET = Offset.of(5, 5);
    private final Offset alignOffset;
    private final Random random;
    private final Cooldown targetCalculationCooldown;

    private final Cooldown minBoostingTime;

    private PositionComponent position;
    private CharacterDriver driver;
    private PositionComponent target;
    private Offset targetOffset;

    /**
     * @param alignOffset the offset is used to align the enemy in front of the target.
     */
    public EnemyWarriorController(Offset alignOffset) {
        this.alignOffset = alignOffset;

        random = new Random();
        targetCalculationCooldown = Cooldown.create(random.nextInt(TARGET_CALC_MIN_COOLDOWN, TARGET_CALC_MAX_COOLDOWN));
        minBoostingTime = Cooldown.create(MIN_BOOSTING_TIME);
    }

    @Override
    public void wrapUp() {
        super.wrapUp();

        position = getComponent(PositionComponent.class);

        driver = getComponent(CharacterDriver.class);
        assert driver != null : "EnemyWarriorController requires a CharacterDriver";
    }

    private void calculateTargetOffset() {
        // TODO: is target is inactive (dead), calculate a random point inside the screen.
        targetOffset = getTargetOffset();
        targetCalculationCooldown.start();
    }

    /**
     * Activates the enemy.
     * @param x horizontal offset of the enemy.
     * @param y vorizontal offset of the enemy.
     * @param target the target position the enemy will try to attack.
     */
    public void activate(int x, int y, PositionComponent target) {
        getPosition().setPosition(x, y);
        this.target = target;
        calculateTargetOffset();
    }

    /**
     * Resets the enemy status.
     */
    public void resetStatus() {
        driver.resetStatus();
    }

    @Override
    public void update() {
        super.update();

        if (gameObject.isInactive() || driver.isAttacking() || isNull(target)) {
            return;
        }

        if (targetCalculationCooldown.isFinished()) {
            calculateTargetOffset();
        }

        if (driver.canAttack() && isCloseEnoughToAttack() && target.getGameObject().isActive()) {
            lookAtTheTarget();
            driver.attack();
            return;
        }

        if (isLaggingBehind() && minBoostingTime.isFinished()) {
            minBoostingTime.start();
            setBoostSpeed();
        }
        else if (minBoostingTime.isFinished()) {
            setBaseSpeed();
        }

        var commands = findCharacterMovements();

        var diagonalMovement = (commands.contains(MOVE_UP) || commands.contains(MOVE_DOWN)) &&
                (commands.contains(MOVE_LEFT) || commands.contains(MOVE_RIGHT));

        if (commands.contains(MOVE_UP)) {
            driver.moveUp(diagonalMovement);
        }
        else if (commands.contains(MOVE_DOWN)) {
            driver.moveDown(diagonalMovement);
        }

        if (commands.contains(MOVE_LEFT)) {
            driver.moveLeft(diagonalMovement);
        }
        else if (commands.contains(MOVE_RIGHT)) {
            driver.moveRight(diagonalMovement);
        }

        if (!commands.contains(MOVE_LEFT) && !commands.contains(MOVE_RIGHT)) {
            // Force looking at the target only if not moving horizontally, so it won't move backwards.
            lookAtTheTarget();
        }
    }

    private boolean isLaggingBehind() {
        // If target is to the left, is not lagging behind
        if (target.getX() < position.getX()) {
            return false;
        }

        var distance = Math.abs(target.getX() - position.getX());

        return distance >= SPEED_BOOST_TARGET_DISTANCE;
    }

    private void setBoostSpeed() {
        driver.setSpeed(AXIS_SPEED_B0OST, DIAGONAL_SPEED_B0OST);
    }

    private void setBaseSpeed() {
        driver.resetSpeed();
    }

    private Set<GameCommand> findCharacterMovements() {
        var commands = EnumSet.noneOf(GameCommand.class);

        // Avoid flickering if can't get in the exact 'target pixel'
        var notCloseEnoughAxisX = !isCloseEnoughAxisX();
        var notCloseEnoughAxisY = !isCloseEnoughAxisY();

        if (notCloseEnoughAxisX && targetOffset.getX() < position.getX()) {
            commands.add(MOVE_LEFT);
        }
        else if (notCloseEnoughAxisX && targetOffset.getX() > position.getX()) {
            commands.add(MOVE_RIGHT);
        }

        if (notCloseEnoughAxisY && targetOffset.getY() < position.getY()) {
            commands.add(MOVE_UP);
        }
        else if (notCloseEnoughAxisY && targetOffset.getY() > position.getY()) {
            commands.add(MOVE_DOWN);
        }

        return commands;
    }

    private boolean isCloseEnoughToAttack() {
        return isCloseEnoughAxisX() && isCloseEnoughAxisY();
    }

    private boolean isCloseEnoughAxisX() {
        int distX = Math.abs(position.getX() - targetOffset.getX());
        return distX <= MIN_ATTACK_DIST_OFFSET.getX();
    }

    private boolean isCloseEnoughAxisY() {
        int distY = Math.abs(position.getY() - targetOffset.getY());
        return distY <= MIN_ATTACK_DIST_OFFSET.getY();
    }

    /**
     * Calculates the target offset with the expected alignment.
     * @return the target offset.
     */
    private Offset getTargetOffset() {
        var alignment = alignOffset.clone();

        // Add randomness value to avoid enemy sprite overlying too much.
        var randomX = random.nextInt(alignment.getX()-15, alignment.getX()+16);
        var randomY = random.nextInt(alignment.getY()-20, alignment.getY()+21);
        alignment = alignment.sum(Offset.of(randomX, randomY));

        if (target.getX() > position.getX()) {
            alignment = alignment.multiply(Offset.of(-1, 1));
        }

        return target.getOffset().sum(alignment);
    }


    private void lookAtTheTarget() {
        System.out.println("lookAtTheTarget");
        if (target.getX() < position.getX() && driver.isFacingRight()) {
            driver.flipDirection();
        }
        else if (target.getX() > position.getX() && driver.isFacingLeft()) {
            driver.flipDirection();

        }
    }
}
