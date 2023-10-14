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
     * Enable/disable the game object.
     * @param isActive if the object is active (true) or not (false).
     */
    void setActive(boolean isActive);

    /**
     * Get this GameObject position. All objects have a position component.
     * @return the game object position component.
     */
    PositionComponent getPosition();

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
     * Get all children components by its type.
     * @param type type of components to be retrieved.
     * @return the components if found.
     * @param <T> component type.
     */
    <T> List<T> getAllComponents(final Class<T> type);

    <T extends Component> T getComponent(Class<T> kind, String name);

    <T extends Component> T getComponentFromChildren(Class<T> kind);

    <T extends Component> List<T> getComponentsFromChildren(Class<T> kind);

    <T> List<T> getServicesFromChildren(Class<T> kind);

    <T> T getService(Class<T> kind);
}
