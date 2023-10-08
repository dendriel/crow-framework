package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.shared.api.game.ColliderType;
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
        super(
                ColliderType.SQUARE,
                DEFAULT_COLLISION_LAYER,
                true,
                Set.of(DEFAULT_COLLISION_LAYER),
                Cooldown.create(cooldown)
        );
    }
}
