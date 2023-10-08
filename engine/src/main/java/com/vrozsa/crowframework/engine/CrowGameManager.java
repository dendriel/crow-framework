package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.game.component.collider.CollisionDetector;
import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.GameObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class CrowGameManager implements GameManager {
    private final Queue<GameObject> gos;
    private final ScreenManager screenManager;
    private final CollisionDetector collisionDetector;

    CrowGameManager(final ScreenManager screenManager) {
        this.screenManager = screenManager;
        gos = new ConcurrentLinkedQueue<>();
        collisionDetector = new CollisionDetector();
    }

    void update() {
        gos.forEach(GameObject::update);
    }

    // Called by gameLoop. May concur with user thread.
    void handleCollision() {
        var collisionObjects =
                gos.stream()
                        .filter(GameObject::isActive)
                        .filter(go -> go.hasComponent(ColliderComponent.class))
                        .map(go -> go.getComponent(ColliderComponent.class))
                        .toList();

        collisionDetector.handle(collisionObjects);
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
