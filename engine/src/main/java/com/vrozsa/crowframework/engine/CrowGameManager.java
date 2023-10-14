package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.game.component.collider.CollisionDetector;
import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.game.PositionComponent;

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

    void lateUpdate() {
        gos.forEach(GameObject::lateUpdate);
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

    @Override
    public void addGameObject(final GameObject go) {
        addWithChildren(go.getPosition());
    }

    private void addWithChildren(final PositionComponent position) {
        var go = position.getGameObject();
        gos.add(go);
        screenManager.renderGO(go);

        position.getChildren()
                .forEach(this::addWithChildren);
    }
}
