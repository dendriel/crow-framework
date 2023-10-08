package com.vrozsa.crowframework.shared.api.game;

import com.vrozsa.crowframework.shared.attributes.Rect;

/**
 * Represents a collider in a game object.
 */
public interface ColliderComponent {

    /**
     * Checks if this collider is able to collide with something right now due to internal state.
     * @return true if the collider is able to collide; false otherwise.
     */
    boolean canCollide();

    /**
     * Checks if this collider can't collide with anything right now due to internal state.
     * @return true if the collider is unable to collide; false otherwise.
     */
    boolean cantCollide();

    /**
     * Gets the type of this collider.
     * @return collider type.
     */
    ColliderType getType();

    /**
     * Get the collision rect, that is, the values used to calculate the collision.
     * @return the collision rect
     */
    Rect getCollisionRect();

    /**
     * Get the game object that owns this component.
     * @return the game object in which this component was added.
     */
    GameObject getGameObject();
}
