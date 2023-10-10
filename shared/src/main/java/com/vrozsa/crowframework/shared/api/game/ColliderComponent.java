package com.vrozsa.crowframework.shared.api.game;

import com.vrozsa.crowframework.shared.attributes.Rect;

import java.util.Set;

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
     * Checks if this collider is able to collide with the target collider.
     * @param target collider to be verified.
     * @return true if this collider is configured to collide with the target's collision layer, or if the target is
     * configured to collide with this collider collision layer; false if the colliders can't interact.
     */
    boolean canCollideWith(ColliderComponent target);

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
     * Get the collision layer from this collider.
     * @return the collision layer.
     */
    String getLayer();

    /**
     * Get the target collision layers this collider can interact with.
     * @return the target collision layers.
     */
    Set<String> getCollisionLayers();

    /**
     * Get the collision rect, that is, the values used to calculate the collision.
     * @return the collision rect
     */
    Rect getCollisionRect();

    /**
     * Get the base rect of this collider.
     * @return the base rect.
     */
    Rect getRect();

    /**
     * Get the game object that owns this component.
     * @return the game object in which this component was added.
     */
    GameObject getGameObject();

    /**
     * Sign to the component that a collision has occurred (collisions are detected by a collision handler).
     */
    void signCollision();
}
