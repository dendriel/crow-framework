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

public class EnemyWarriorController extends AbstractComponent {
    private static final int TARGET_CALC_MIN_COOLDOWN = 500;
    private static final int TARGET_CALC_MAX_COOLDOWN = 1000;
    private final PositionComponent target;
    private final Offset alignOffset;
    private final Random random;
    private final Cooldown targetCalculationCooldown;

    private PositionComponent position;
    private CharacterDriver driver;

    private Offset targetOffset;

    /**
     * @param target the target position the enemy will try to attack.
     * @param alignOffset the offset is used to align the enemy in front of the target.
     */
    public EnemyWarriorController(PositionComponent target, Offset alignOffset) {
        this.target = target;
        this.alignOffset = alignOffset;

        random = new Random();
        targetCalculationCooldown = Cooldown.create(random.nextInt(TARGET_CALC_MIN_COOLDOWN, TARGET_CALC_MAX_COOLDOWN));
    }

    @Override
    public void wrapUp() {
        super.wrapUp();

        position = getComponent(PositionComponent.class);

        driver = getComponent(CharacterDriver.class);
        assert driver != null : "EnemyWarriorController requires a CharacterDriver";
    }

    private void calculateTargetOffset() {
        targetOffset = getTargetOffset();
        targetCalculationCooldown.start();
    }

    @Override
    public void update() {
        super.update();

        if (gameObject.isInactive()) {
            return;
        }

        if (targetCalculationCooldown.isFinished()) {
            calculateTargetOffset();
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

    private Set<GameCommand> findCharacterMovements() {
        var commands = EnumSet.noneOf(GameCommand.class);

        if (targetOffset.getX() < position.getX()) {
            commands.add(MOVE_LEFT);
        }
        else if (targetOffset.getX() > position.getX()) {
            commands.add(MOVE_RIGHT);
        }

        if (targetOffset.getY() < position.getY()) {
            commands.add(MOVE_UP);
        }
        else if (targetOffset.getY() > position.getY()) {
            commands.add(MOVE_DOWN);
        }

        return commands;
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
        if (target.getX() < position.getX() && driver.isFacingRight()) {
            driver.flipDirection();
        }
        else if (target.getX() > position.getX() && driver.isFacingLeft()) {
            driver.flipDirection();
        }
    }
}
