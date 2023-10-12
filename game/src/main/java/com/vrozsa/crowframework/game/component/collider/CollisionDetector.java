package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderType;
import com.vrozsa.crowframework.shared.api.game.PositionComponent;
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
        int sourceWeight = source.getWeight();
        int targetWeight = target.getWeight();
        if (sourceWeight == 0 || targetWeight == 0) {
            return;
        }

        if (source.cantCollideWith(target) && target.cantCollideWith(source)) {
            return;
        }

        var totalWeight = source.getWeight() + target.getWeight();
        var sourceProp = (float)source.getWeight() / totalWeight;
        var targetProp = (float)target.getWeight() / totalWeight;

        if (source.isMoving() && !target.isMoving()) {
            var offset = source.getLastOffsetAdded();
            applyMovement(offset, source.getGameObject().getPosition(), sourceProp, target.getGameObject().getPosition(), targetProp);
        }
        else if (target.isMoving() && !source.isMoving()) {
            var offset = target.getLastOffsetAdded();
            applyMovement(offset, target.getGameObject().getPosition(), targetProp, source.getGameObject().getPosition(), sourceProp);
        }
        else if (source.isMoving() && target.isMoving()) {

            var offsetA = source.getLastOffsetAdded();
            var offsetB = target.getLastOffsetAdded();

            applyMovement(offsetA, source.getGameObject().getPosition(), sourceProp, target.getGameObject().getPosition(), targetProp);
            applyMovement(offsetB, target.getGameObject().getPosition(), targetProp, source.getGameObject().getPosition(), sourceProp);
        }
    }

    private static void applyMovement(final Offset offset,
                                      final PositionComponent posA, final float proportionA,
                                      final PositionComponent posB, final float proportionB
    ) {
        var offsetA = calcProportionalOffset(offset, proportionA, 1);
        posB.addOffset(offsetA);

        var offsetB = calcProportionalOffset(offset, proportionB, -1);
        posA.addOffset(offsetB);
    }

    private static Offset calcProportionalOffset(final Offset offset, final float proportion, final int inverter) {
        int x;
        if (offset.getX() > 0) {
            x = (int)Math.ceil(offset.getX() * proportion * inverter);
        }
        else {
            x = (int)Math.floor(offset.getX() * proportion * inverter);
        }

        int y;
        if (offset.getY() > 0) {
            y = (int)Math.ceil(offset.getY() * proportion * inverter);
        }
        else {
            y = (int)Math.floor(offset.getY() * proportion * inverter);
        }

        return Offset.of(x, y);
    }
}
