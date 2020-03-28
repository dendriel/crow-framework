package com.rozsa.crow.game.api;

import com.rozsa.crow.game.GameObject;
import com.rozsa.crow.game.component.Position;

public interface Component extends Identifiable {
    GameObject getGameObject();

    void setGameObject(GameObject gameObject);

    Position getPosition();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isDisabled();

    void wrapUp();
}
