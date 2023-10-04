package com.vrozsa.crowframework.shared.api.game;

import java.util.List;

public interface GameObject {

    void update();

    void lateUpdate();

    /**
     * Checks if this GO is inactive
     * @return true if inactive; false if active.
     */
    boolean isInactive();

    <T extends Component> T getComponent(Class<T> kind);

    <T extends Component> T getComponent(Class<T> kind, String name);

    <T extends Component> T getComponentFromChildren(Class<T> kind);

    <T extends Component> List<T> getComponentsFromChildren(Class<T> kind);

    <T> List<T> getServicesFromChildren(Class<T> kind);

    <T> T getService(Class<T> kind);
}
