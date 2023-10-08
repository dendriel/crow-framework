package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderType;
import com.vrozsa.crowframework.shared.attributes.Rect;

import java.util.Collection;
import java.util.Objects;

/**
 * Handles collision between game objects.
 */
public class CollisionDetector {

    /**
     * Checks which objects collides between themselves and call the appropriate collider callbacks.
     * @param colliders colliders to be checked. *Filtering of inactive or invalid objects must take place beforehand.
     */
    public void handle(final Collection<ColliderComponent> colliders) {
        var validColliders = colliders.stream()
                .filter(ColliderComponent::canCollide)
                .toList();

        // Test each collider at beginning of the list with the next colliders of the list. Avoid retesting the same
        // collider combination.
        for (var i = 0; i < validColliders.size(); i++) {
            var source = validColliders.get(i);

            for (var j = i+1; j < validColliders.size(); j++) {
                var target = validColliders.get(j);

                handleCollisionBetween(source, target);
            }
        }
    }

    // this may not scale, but will do for now.
    private static void handleCollisionBetween(final ColliderComponent source, final ColliderComponent target) {
        var hasCollision = false;
        if (source.getType() == ColliderType.SQUARE && source.getType() == target.getType()) {
            hasCollision = checkCollision(source, target);
        }

        if (!hasCollision) {
            return;
        }

        var sourceGo = source.getGameObject();
        var targetGo = target.getGameObject();

        var sourceCollisionHandler = sourceGo.getComponent(BaseCollisionHandler.class);
        if (!Objects.isNull(sourceCollisionHandler)) {
            sourceCollisionHandler.handle(sourceGo, targetGo);
        }

        var targetCollisionHandler = targetGo.getComponent(BaseCollisionHandler.class);
        if (!Objects.isNull(targetCollisionHandler)) {
            targetCollisionHandler.handle(targetGo, sourceGo);
        }
    }

    private static boolean checkCollision(final ColliderComponent sourceCollider, final ColliderComponent targetCollider) {
        Rect source = sourceCollider.getCollisionRect();
        Rect target = targetCollider.getCollisionRect();

        return source.getX() < target.getX() + target.getWidth() &&
            source.getX() + source.getWidth() > target.getX() &&
            source.getY() < target.getY() + target.getHeight() &&
            source.getY() + source.getHeight() > target.getY();
    }
}
