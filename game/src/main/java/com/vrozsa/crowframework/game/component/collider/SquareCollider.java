package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.shared.api.game.ColliderType;

import java.util.Set;

/**
 * Represents a square collider.
 */
public class SquareCollider extends BaseCollider {
    public SquareCollider() {
        super(ColliderType.SQUARE, DEFAULT_COLLISION_LAYER, true, Set.of(DEFAULT_COLLISION_LAYER));
    }
}
