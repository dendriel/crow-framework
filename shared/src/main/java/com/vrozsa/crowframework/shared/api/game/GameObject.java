package com.vrozsa.crowframework.shared.api.game;

import java.util.List;

/**
 * Game Objects are the in-game representation of entities and elements.
 * (out-game elements would be menus, ui, HUD, etc)
 */
public interface GameObject {
    /**
     * Updates the game object state.
     */
    void update();

    /**
     * Late updates the game object state.
     * This runs after the update phase().
     */
    void lateUpdate();

    /**
     * Earlier updates the game object state. Useful for clearing this that the late update let in place for the collision
     * handling phase (that runs before the earlierUpdate).
     * This runs before the update() phase.
     */
    void earlyUpdate();

    /**
     * Checks if this GO is active
     * @return true if active; false if inactive.
     */
    boolean isActive();

    /**
     * Checks if this GO is inactive
     * @return true if inactive; false if active.
     */
    boolean isInactive();

    /**
     * Activate/deactivate the game object.
     * @param isActive if the object is active (true) or not (false).
     */
    void setActive(boolean isActive);

    /**
     * Activate this game object.
     */
    void activate();

    /**
     * Deactivate this game object.
     */
    void deactivate();

    /**
     * Get this GameObject position. All objects have a position component.
     * @return the game object position component.
     */
    Position getPosition();

    /**
     * Add a new component to this game object.
     * @param component the component to be added.
     */
    void addComponent(final Component component);

    /**
     * Checks if this game object has at least one component of the given kind.
     * @param type component to be checked.
     * @return true if the GO has the component; false otherwise
     */
    <T> boolean hasComponent(Class<T> type);

    /**
     * Get a child component by its type.
     * @param type type of component to be retrieved.
     * @return the component if found.
     * @param <T> component type.
     */
    <T> T getComponent(Class<T> type);

    /**
     * Get a child component by its type and name
     * @param type type of component to be retrieved.
     * @param name name of the component.
     * @return the component if found.
     * @param <T> component type.
     */
    <T extends Component> T getComponent(Class<T> type, String name);

    /**
     * Get all components by its type.
     * @param type type of components to be retrieved.
     * @return the components if found.
     * @param <T> component type.
     */
    <T> List<T> getAllComponents(final Class<T> type);

    /**
     * Get a component by its type and include components in children game-objects.
     * @param type type of component to be retrieved.
     * @return the component if found.
     * @param <T> component type.
     */
    <T extends Component> T getComponentFromChildren(Class<T> type);

    /**
     * Gets all components by its type and include components in children game-objects.
     * @param type type of component to be retrieved.
     * @return the component if found.
     * @param <T> component type.
     */
    <T extends Component> List<T> getComponentsFromChildren(Class<T> type);
}
