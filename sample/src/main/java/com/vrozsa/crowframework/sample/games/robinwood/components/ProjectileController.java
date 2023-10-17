package com.vrozsa.crowframework.sample.games.robinwood.components;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.shared.api.game.CollisionHandler;
import com.vrozsa.crowframework.shared.api.game.Direction;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.time.Cooldown;

import java.util.Objects;

public class ProjectileController extends AbstractComponent implements CollisionHandler {
    private final int speed;
    private final Cooldown lifetimeCooldown;
    private Direction direction = Direction.NONE;
    private boolean facingRight = true;
    private int damage;

    public ProjectileController(int speed, int lifetime, int damage) {
        this.speed = speed;
        this.damage = damage;
        lifetimeCooldown = Cooldown.create(lifetime);
    }

    @Override
    public void update() {
        super.update();

        if (getGameObject().isInactive()) {
            return;
        }

        int increment = speed;
        if (direction == Direction.LEFT) {
            increment *= -1;
        }

        var position = getPosition();

        position.addOffset(Offset.of(increment, 0));

        // Disable if out of the screen.
        if (lifetimeCooldown.isFinished()) {
            gameObject.setActive(false);
        }
    }

    public void activate(final int x, final int y, final Direction direction) {
        if (lifetimeCooldown.isWaiting()) {
            return;
        }

        getPosition().setPosition(x, y);
        setDirection(direction);
        lifetimeCooldown.start();
        gameObject.setActive(true);
    }

    public void deactivate() {
        gameObject.setActive(false);
    }

    private void setDirection(final Direction direction) {
        this.direction = direction;

        if (direction == Direction.RIGHT && !facingRight) {
            facingRight = true;
            var renderer = getComponent(Renderer.class);
            renderer.setFlipX(false);
        }

        if (direction == Direction.LEFT && facingRight) {
            facingRight = false;
            var renderer = getComponent(Renderer.class);
            renderer.setFlipX(true);
        }
    }
    @Override
    public void handleCollision(GameObject source, GameObject target) {
        var charDriver = target.getComponent(CharacterDriver.class);
        if (Objects.isNull(charDriver)) {
            // Collided with something not a character.
            return;
        }

        charDriver.takeDamage(damage);
        this.deactivate();
    }
}
