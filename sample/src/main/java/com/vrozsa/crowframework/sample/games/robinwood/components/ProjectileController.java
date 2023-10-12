package com.vrozsa.crowframework.sample.games.robinwood.components;

import com.vrozsa.crowframework.game.component.BaseComponent;
import com.vrozsa.crowframework.shared.api.game.Direction;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Offset;
import lombok.RequiredArgsConstructor;

import static com.vrozsa.crowframework.sample.TestValues.CHARS_SPRITE_SIZE;
import static com.vrozsa.crowframework.sample.TestValues.SCREEN_WIDTH;

@RequiredArgsConstructor
public class ProjectileController extends BaseComponent {
    private final int speed;
    private Direction direction = Direction.NONE;
    private boolean facingRight = true;

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
        if (position.getX() < CHARS_SPRITE_SIZE.getWidth()*-1 || position.getX() >= SCREEN_WIDTH + CHARS_SPRITE_SIZE.getWidth()) {
            getGameObject().setActive(false);
        }
    }

    public void setDirection(final Direction direction) {
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

    public void setPosition(final int x, final int y) {
        getPosition().setPosition(x, y);
    }
}
