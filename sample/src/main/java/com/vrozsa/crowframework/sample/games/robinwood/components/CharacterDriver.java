package com.vrozsa.crowframework.sample.games.robinwood.components;

import com.vrozsa.crowframework.game.component.BaseComponent;
import com.vrozsa.crowframework.game.component.animation.AnimatedRenderer;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.time.Cooldown;

import static com.vrozsa.crowframework.shared.api.game.Direction.LEFT;
import static com.vrozsa.crowframework.shared.api.game.Direction.RIGHT;

/**
 * The character driver controls the character game object. This way, we can just issue commands to the driver
 * and let it handle the character state by itself.
 */
public class CharacterDriver extends BaseComponent {
    private static final String ANIM_WALK_KEY = "walk";
    private static final String ANIM_IDLE_KEY = "idle";
    private static final String ANIM_ATTACK_KEY = "attack";

    private int axisSpeed;
    private int diagonalSpeed;
    private boolean facingRight;
    private boolean isMoving;
    private boolean isAttacking;
    private String projectileType;
    private Cooldown shootCooldown;
    private final Offset projectileSpawnOffset;

    private AnimatedRenderer animatedRenderer;
    private ProjectileHandler projectileHandler;

    public CharacterDriver(
            boolean facingRight, int axisSpeed, int diagonalSpeed, String projectileType, int shootCooldown, Offset projectileSpawnOffset) {
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

        animatedRenderer = getComponent(AnimatedRenderer.class);
        assert animatedRenderer != null : "CharacterDriver requires a AnimatedRenderer!";

        var attackAnim = animatedRenderer.getAnimation(ANIM_ATTACK_KEY);
        assert attackAnim != null : "AnimatedRenderer requires an 'attack' animation!";
        attackAnim.addTriggerEndedObserver(this::onAttackAnimationEnded);

        projectileHandler = getComponent(ProjectileHandler.class);
        assert projectileHandler != null : "CharacterDriver requires a ProjectileHandler!";
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
        getPosition().addOffset(Offset.of(speed * x, speed * y));

        setWalking();
    }

    public void flipDirection() {
        var renderer = getComponent(Renderer.class);
        renderer.setFlipX(facingRight);
        facingRight = !facingRight;
    }

    public void shoot() {
        if (shootCooldown.isWaiting()) {
            return;
        }

        var direction = facingRight ? RIGHT : LEFT;
        var pos = getPosition();

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

    @Override
    public void lateUpdate() {
        /*
         * Handle animation state changing on late update so everything can take place in time. An animation ended trigger
         * may happen after the driver update() is called, thus we have to control this in lateUpdate().
         */
        super.lateUpdate();
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
