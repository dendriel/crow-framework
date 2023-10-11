package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.shared.api.game.ColliderType;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.time.Cooldown;

import java.util.Set;

/**
 * Represents a square collider.
 */
public class SquareCollider extends BaseCollider {

    public SquareCollider() {
        this(DEFAULT_COOLDOWN_MILLIS);
    }
    public SquareCollider(final long cooldown) {
        this(cooldown, DEFAULT_COLLISION_LAYER, 0, Set.of(DEFAULT_COLLISION_LAYER));
    }

    public SquareCollider(final long cooldown, final String collisionLayer, final int density, final Set<String> collidesWith) {
        this(cooldown, collisionLayer, density, collidesWith, null);
    }

    public SquareCollider(
            final long cooldown, final String collisionLayer, final int density, final Set<String> collidesWith, final Rect rect) {
        super(
                ColliderType.SQUARE,
                collisionLayer,
                true,
                density,
                collidesWith,
                Cooldown.create(cooldown),
                rect
        );
    }
}
