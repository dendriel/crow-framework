package com.vrozsa.crowframework.shared.api.game;


public interface Component extends Identifiable {
    GameObject getGameObject();

    void setGameObject(GameObject gameObject);

//    Component getPosition();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isDisabled();

    void wrapUp();

    void update();

    void lateUpdate();
}
