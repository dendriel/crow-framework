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
        isMoving = true;
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

    private void setWalking() {
        animatedRenderer.setOnlyEnabled(ANIM_WALK_KEY);
    }

    private void setIdle() {
        animatedRenderer.setOnlyEnabled(ANIM_IDLE_KEY);
    }

    private void setAttacking() {
        animatedRenderer.setAllAnimationsInactive();
        animatedRenderer.trigger(ANIM_ATTACK_KEY);
    }

    @Override
    public void update() {
        super.update();

        if (isMoving) {
            isMoving = false;
            return;
        }

        // While attacking, will set IDLE animation if is the last frame. Otherwise, the triggered animation will turn
        // of after the last frame and we will have a flickering effect.
        if (animatedRenderer.isAnimationActive(ANIM_ATTACK_KEY) &&
                !animatedRenderer.isLastAnimationFrame(ANIM_ATTACK_KEY)) {
            return;
        }

        setIdle();

    }

//    private void shoot() {
//        if (arrowGO.isActive()) {
//            logger.debug("Arrow already flying...");
//            return;
//        }
//
//        logger.debug("Arrow fired!");
//
//        var arrowController = arrowGO.getComponent(CollisionBlocking.ArrowController.class);
//        arrowController.setDirection(facingRight ? Direction.RIGHT : Direction.LEFT);
//        arrowController.setPosition(getPosition().getOffset());
//        arrowGO.setActive(true);
//    }


}
