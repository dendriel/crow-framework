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
     */
    void lateUpdate();

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
     * @param kind component to be checked.
     * @return true if the GO has the component; false otherwise
     */
    <T> boolean hasComponent(Class<T> kind);

    <T> T getComponent(Class<T> kind);

    <T extends Component> T getComponent(Class<T> kind, String name);

    <T extends Component> T getComponentFromChildren(Class<T> kind);

    <T extends Component> List<T> getComponentsFromChildren(Class<T> kind);

    <T> List<T> getServicesFromChildren(Class<T> kind);

    <T> T getService(Class<T> kind);
}
