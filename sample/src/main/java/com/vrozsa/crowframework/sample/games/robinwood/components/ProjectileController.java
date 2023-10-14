package com.vrozsa.crowframework.sample.games.robinwood.components;

import com.vrozsa.crowframework.game.component.BaseComponent;
import com.vrozsa.crowframework.shared.api.game.Direction;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.time.Cooldown;

public class ProjectileController extends BaseComponent {
    private final int speed;
    private final Cooldown lifetimeCooldown;
    private Direction direction = Direction.NONE;
    private boolean facingRight = true;

    public ProjectileController(int speed, int lifetime) {
        this.speed = speed;
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
}
