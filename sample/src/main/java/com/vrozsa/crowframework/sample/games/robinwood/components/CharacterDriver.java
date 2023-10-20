package com.vrozsa.crowframework.sample.games.robinwood.components;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.game.component.animation.AnimatedRenderer;
import com.vrozsa.crowframework.shared.api.game.PositionComponent;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.time.Cooldown;

import java.util.Objects;

import static com.vrozsa.crowframework.shared.api.game.Direction.LEFT;
import static com.vrozsa.crowframework.shared.api.game.Direction.RIGHT;

/**
 * The character driver controls the character game object. This way, we can just issue commands to the driver
 * and let it handle the character state by itself.
 */
public class CharacterDriver extends AbstractComponent {
    private static final String ANIM_WALK_KEY = "walk";
    private static final String ANIM_IDLE_KEY = "idle";
    private static final String ANIM_ATTACK_KEY = "attack";

    private final int startingAxisSpeed;
    private final int startingDiagonalSpeed;
    private int axisSpeed;
    private int diagonalSpeed;
    private boolean facingRight;
    private boolean isMoving;
    private boolean isAttacking;
    private String projectileType;
    private Cooldown shootCooldown;
    private final Offset projectileSpawnOffset;

    private int leftLimitX = 0;

    private PositionComponent position;
    private AnimatedRenderer animatedRenderer;
    private ProjectileHandler projectileHandler;
    private CharacterStatus characterStatus;
    private int baseRenderingLayer;

    public CharacterDriver(
            boolean facingRight, int axisSpeed, int diagonalSpeed, String projectileType, int shootCooldown, Offset projectileSpawnOffset) {
        this.startingAxisSpeed = axisSpeed;
        this.startingDiagonalSpeed = diagonalSpeed;
        this.axisSpeed = axisSpeed;
        this.diagonalSpeed = diagonalSpeed;
        this.projectileType = projectileType;
        this.facingRight = facingRight;

        this.projectileSpawnOffset = projectileSpawnOffset;
        this.shootCooldown = Cooldown.create(shootCooldown);
    }

    @Override
    public void wrapUp() {
        super.wrapUp();

        position = getPosition();

        animatedRenderer = getComponent(AnimatedRenderer.class);
        assert animatedRenderer != null : "CharacterDriver requires a AnimatedRenderer!";
        baseRenderingLayer = animatedRenderer.getLayer();

        var attackAnim = animatedRenderer.getAnimation(ANIM_ATTACK_KEY);
        assert attackAnim != null : "AnimatedRenderer requires an 'attack' animation!";
        attackAnim.addTriggerEndedObserver(this::onAttackAnimationEnded);

        projectileHandler = getComponent(ProjectileHandler.class);
        assert projectileHandler != null : "CharacterDriver requires a ProjectileHandler!";

        characterStatus = getComponent(CharacterStatus.class);
    }

    public void moveRight(boolean diagonalMovement) {
        move(1, 0, diagonalMovement);
        if (!facingRight) {
            flipDirection();
        }
    }

    public void moveLeft(boolean diagonalMovement) {
        move(-1, 0, diagonalMovement);
        if (facingRight) {
            flipDirection();
        }
    }

    public void moveDown(boolean diagonalMovement) {
        move(0, 1, diagonalMovement);
    }

    public void moveUp(boolean diagonalMovement) {
        move(0, -1, diagonalMovement);
    }

    private void move(int x, int y, boolean diagonalMovement) {
        var speed = diagonalMovement ? diagonalSpeed : axisSpeed;
        var offsetToAdd = Offset.of(speed * x, speed * y);

        if (isInvalidLeftMovement(offsetToAdd.getX())) {
            offsetToAdd.setX(0);
        }

        position.addOffset(offsetToAdd);

        if (!isAttacking) {
            setWalking();
        }
    }

    private boolean isInvalidLeftMovement(int offsetX) {
        var currPos = position.getOffset();
        return (currPos.getX() + offsetX) < leftLimitX;
    }

    /**
     * Set the minimum X the character can before being blocked.
     * @param leftLimitX Left X limit.
     */
    public void setLeftLimitX(int leftLimitX) {
        this.leftLimitX = leftLimitX;
    }

    /**
     * Sets the character speed.
     * @param axisSpeed X and Y speed.
     * @param diagonalSpeed diagonal speed, used when moving diagonally. Should be less than axisSpeed because diagonal
     *                      movement is the character moving in two axis at the same time.
     */
    public void setSpeed(int axisSpeed, int diagonalSpeed) {
        this.axisSpeed = axisSpeed;
        this.diagonalSpeed = diagonalSpeed;
    }

    /**
     * Resets speed to is starting values.
     */
    public void resetSpeed() {
        this.axisSpeed = startingAxisSpeed;
        this.diagonalSpeed = startingDiagonalSpeed;
    }

    public int getAxisSpeed() {
        return axisSpeed;
    }

    public int getDiagonalSpeed() {
        return diagonalSpeed;
    }

    public void flipDirection() {
        var renderer = getComponent(Renderer.class);
        renderer.setFlipX(facingRight);
        facingRight = !facingRight;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public boolean isFacingLeft() {
        return !facingRight;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean canAttack() {
        return shootCooldown.isFinished();
    }

    public void attack() {
        if (shootCooldown.isWaiting()) {
            return;
        }

        var direction = facingRight ? RIGHT : LEFT;
        var pos = position;

        var spawnOffsetY = projectileSpawnOffset.getY();
        var spawnOffsetX = projectileSpawnOffset.getX();
        spawnOffsetX *= facingRight ? 1 : -1;

        projectileHandler.spawnProjectile(projectileType, pos.getX() + spawnOffsetX, pos.getY() - spawnOffsetY, direction);
        shootCooldown.start();
        setAttacking();
    }

    private void onAttackAnimationEnded() {
        isAttacking = false;
    }

    private void setWalking() {
        isMoving = true;
        animatedRenderer.setOnlyEnabled(ANIM_WALK_KEY);
    }

    private void setIdle() {
        animatedRenderer.setOnlyEnabled(ANIM_IDLE_KEY);
    }

    private void setAttacking() {
        isAttacking = true;
        animatedRenderer.setAllAnimationsInactive();
        animatedRenderer.trigger(ANIM_ATTACK_KEY);
    }

    public void takeDamage(int value) {
        if (Objects.isNull(characterStatus)) {
            return;
        }

        characterStatus.removeLife(value);
    }

    /**
     * Reset the status to its initial state.
     */
    public void resetStatus() {
        characterStatus.reset();
    }

    @Override
    public void lateUpdate() {
        /*
         * Handle animation state changing on late update so everything can take place in time. An animation ended trigger
         * may happen after the driver update() is called, thus we have to control this in lateUpdate().
         */
        super.lateUpdate();

        if (gameObject.isInactive()) {
            return;
        }

        // Keep setting adding the current Y position to the rendering layer so characters close to the bottom appears
        // in front of characters more close to the top of the screen.
        int newRenderingLayer = baseRenderingLayer + position.getAbsolutePosY();
        animatedRenderer.setLayer(newRenderingLayer);

        if (isMoving) {
            isMoving = false;
            return;
        }

        if (isAttacking) {
            return;
        }

        setIdle();
    }
}
