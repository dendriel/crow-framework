package com.rozsa.shared.api.game;


// TODO: remove this
public interface Component extends Identifiable {
    GameObject getGameObject();

    void setGameObject(GameObject gameObject);

//    Position getPosition();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isDisabled();

    void wrapUp();

    void update();

    void lateUpdate();
}
