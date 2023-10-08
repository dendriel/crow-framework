package com.vrozsa.crowframework.shared.api.game;

/**
 * Components are the building blocks of a Game Object. They add functionality to Game Objects.
 */
public interface Component extends Identifiable {
    /**
     * Set the game object that owns this component.
     * @param gameObject the game object in which this component was added.
     */
    void setGameObject(GameObject gameObject);

    /**
     * Get the game object that owns this component.
     * @return the game object in which this component was added.
     */
    GameObject getGameObject();

    /**
     * Get the current position of this component (most useful for components that are visible in the world).
     * @return the component position.
     */
    PositionComponent getPosition();

    /**
     * Checks if this component is enabled.
     * @return true if enabled; false if disabled.
     */
    boolean isEnabled();

    /**
     * Enable/disable this component
     * @param enabled if the component must be enabled (true); or disabled (false).
     */
    void setEnabled(boolean enabled);

    /**
     * Checks if this component is disabled.
     * @return true if disabled; false if enabled.
     */
    boolean isDisabled();

    /**
     * WrapUp the initial component state (called once after the component is added to the parent game object).
     */
    void wrapUp();

    /**
     * Update the component state.
     */
    void update();

    /**
     * Late update the component state.
     */
    void lateUpdate();
}
