package com.vrozsa.crowframework.shared.api.game;

/**
 * Component able to handle collisions.
 */
@FunctionalInterface
public interface CollisionHandler {
    /**
     * Handles collision between game objects.
     * @param source source game object.
     * @param target target object in which the source has collided.
     */
    void handle(final GameObject source, final GameObject target);
}
