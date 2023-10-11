package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderType;
import com.vrozsa.crowframework.shared.attributes.Offset;

import java.util.List;
import java.util.Objects;

/**
 * Handles collision between game objects.
 */
public class CollisionDetector {

    /**
     * Checks which objects collides between themselves and call the appropriate collider callbacks.
     * @param colliders colliders to be checked. *Filtering of inactive or invalid objects must take place beforehand.
     */
    public void handle(final List<ColliderComponent> colliders) {
        // Test each collider at beginning of the list with the next colliders of the list. Avoid retesting the same
        // collider combination.
        for (var i = 0; i < colliders.size(); i++) {
            var source = colliders.get(i);

            for (var j = i+1; j < colliders.size(); j++) {
                var target = colliders.get(j);

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
        if (!Objects.isNull(sourceCollisionHandler) &&
                source.canTriggerCollision() &&
                source.canCollideWith(target)) {
            sourceCollisionHandler.handle(sourceGo, targetGo);
            source.signCollision();
        }

        var targetCollisionHandler = targetGo.getComponent(BaseCollisionHandler.class);
        if (!Objects.isNull(targetCollisionHandler) &&
                target.canTriggerCollision() &&
                target.canCollideWith(source)) {
            targetCollisionHandler.handle(targetGo, sourceGo);
            target.signCollision();
        }

        if (source.getGameObject().isActive() && target.getGameObject().isActive()) {
            handleInteraction(source, target);
        }
    }

    private static boolean checkCollision(final ColliderComponent sourceCollider, final ColliderComponent targetCollider) {
        if (!sourceCollider.canCollideWith(targetCollider) && !targetCollider.canCollideWith(sourceCollider)) {
            return false;
        }

        var source = sourceCollider.getCollisionRect();
        var target = targetCollider.getCollisionRect();

        return source.getX() < target.getX() + target.getWidth() &&
            source.getX() + source.getWidth() > target.getX() &&
            source.getY() < target.getY() + target.getHeight() &&
            source.getY() + source.getHeight() > target.getY();
    }

    private static void handleInteraction(final ColliderComponent source, final ColliderComponent target) {
        int sourceDensity = source.getDensity();
        int targetDensity = target.getDensity();
        if (sourceDensity == 0 || targetDensity == 0) {
            return;
        }

        if (source.cantCollideWith(target) && target.cantCollideWith(source)) {
            return;
        }

        ColliderComponent bigger = sourceDensity > targetDensity ? source : target;
        ColliderComponent smaller = sourceDensity > targetDensity ? target : source;

        float densityDiff = bigger.getDensity() / smaller.getDensity();
        // bigger element is X times bigger, so it won't move.
        if (densityDiff >= 3f) {
            //source outweighs target, so target has to move.
            moveAwayFrom(smaller, bigger);
            return;
        }

        // calculate proportion of movement.
        // move each part relative to its proportion.
    }

    private static void moveAwayFrom(final ColliderComponent source, final ColliderComponent target) {
        var sourceRect = source.getCollisionRect();
        var targetRect = target.getCollisionRect();

        var sourceTopLeft = sourceRect.getX();
        var sourceTopRight = sourceRect.getX() + sourceRect.getWidth();
        var sourceCenterX = sourceRect.getX() + (sourceRect.getWidth() / 2);

        var targetTopLeft = targetRect.getX();
        var targetTopRight = targetRect.getX() + targetRect.getWidth();
        var targetCenterX = targetRect.getX() + (targetRect.getWidth() / 2);

        // source touching the target on the left side
        if (sourceTopLeft < targetTopLeft &&
            sourceTopRight > targetTopLeft) {
            var fallback = sourceTopRight - targetTopLeft;
            source.getGameObject().getPosition().addOffset(Offset.of(fallback*-1, 0));
        }
        // source touching the target on the right side
        else if (sourceTopRight > targetTopRight &&
            sourceTopLeft < targetTopRight) {
            var fallback = targetTopRight - sourceTopLeft;
            source.getGameObject().getPosition().addOffset(Offset.of(fallback, 0));
        }
    }
}
