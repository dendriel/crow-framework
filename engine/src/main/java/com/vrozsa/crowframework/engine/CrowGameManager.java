package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.shared.api.game.GameObject;

import java.util.ArrayList;
import java.util.List;

class CrowGameManager implements GameManager {
    private final List<GameObject> gos;
    private final ScreenManager screenManager;

    CrowGameManager(final ScreenManager screenManager) {
        this.screenManager = screenManager;
        gos = new ArrayList<>();
    }

    void update() {
        gos.forEach(GameObject::update);
    }

    void lateUpdate() {
        gos.forEach(GameObject::lateUpdate);
    }

    @Override
    public void addGameObject(final GameObject go) {
        gos.add(go);
        screenManager.renderGO(go);
    }
}
