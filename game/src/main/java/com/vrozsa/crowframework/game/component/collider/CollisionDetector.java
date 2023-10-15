package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderType;
import com.vrozsa.crowframework.shared.api.game.PositionComponent;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handles collision between game objects.
 */
public class CollisionDetector {
    private static final LoggerService logger = LoggerService.of(CollisionDetector.class);

    /**
     * Checks which objects collides between themselves and call the appropriate collider callbacks.
     * @param colliders colliders to be checked. *Filtering of inactive or invalid objects must take place beforehand.
     */
    public void handle(final List<ColliderComponent> colliders) {
        final int maxHandles = 10;
        var handlesCount = new AtomicInteger();
        var collisionDetected = false;

        do {
            logger.debug(() -> handlesCount.get() > 0,"\nRedetecting collision. Round: {0}", handlesCount);
            collisionDetected = checkAndHandleCollision(colliders);
        } while (collisionDetected && handlesCount.incrementAndGet() <= maxHandles);
    }

    public boolean checkAndHandleCollision(final List<ColliderComponent> colliders) {
        boolean isCollisionDetected = false;

        // Test each collider at beginning of the list with the next colliders of the list. Avoid retesting the same
        // collider combination.
        for (var i = 0; i < colliders.size(); i++) {
            var source = colliders.get(i);

            for (var j = i+1; j < colliders.size(); j++) {
                var target = colliders.get(j);

                var sourceOffset = source.getGameObject().getPosition().getOffset();
                var targetOffset = target.getGameObject().getPosition().getOffset();

                handleCollisionBetween(source, target);

                if (!sourceOffset.equals(source.getGameObject().getPosition().getOffset()) ||
                        !targetOffset.equals(target.getGameObject().getPosition().getOffset())) {
                    isCollisionDetected = true;
                }
            }
        }
        return isCollisionDetected;
    }

    /**
     * Handle collision between source and target colliders.
     * @param source source collider.
     * @param target target collider.
     */
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

        var sourceCollisionHandler = sourceGo.getComponent(AbstractCollisionHandler.class);
        if (!Objects.isNull(sourceCollisionHandler) &&
                source.canTriggerCollision() &&
                source.canCollideWith(target)) {
            sourceCollisionHandler.handle(sourceGo, targetGo);
            source.signCollision();
        }

        var targetCollisionHandler = targetGo.getComponent(AbstractCollisionHandler.class);
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

        var totalWeight = source.getWeight() + (long)target.getWeight();
        var sourceProp = (double)sourceWeight / totalWeight;
        var targetProp = (double)targetWeight / totalWeight;

//        if ( sourceWeight > targetWeight && (sourceWeight / targetWeight) >= 10) {
//            sourceProp = 1;
//            targetProp = 0;
//        }
//        else if (targetWeight > sourceWeight && (targetWeight / sourceWeight) >= 10) {
//            sourceProp = 0;
//            targetProp = 1;
//        }

        if (source.isMoving() && !target.isMoving()) {
            var offset = source.getOffsetAddedLastFrame();
            if (targetWeight > sourceWeight && (targetWeight / sourceWeight) >= 10) {
                source.clearOffsetAddedLastFrame();
                // if stopped element overweight moving object, invert the movement.
                applyMovement(offset, source.getGameObject().getPosition(), 0, -1);
                logger.info("applied inverse movement on srouce {0} from target {1}", source, target);
//                source.clearIsMoving();
                // Now it is moving in the opposite direction.
                return;
            }

            logger.info("source {0} vs target {1} - prop: {2}/{3} - offset: {4}", source, target, sourceProp, targetProp, offset);

            // Clear the offset because it was applied and we don't wan't to reapply if redetecting collisions in this frame.
            source.clearOffsetAddedLastFrame();
            applyMovement(offset, source.getGameObject().getPosition(), sourceProp, target.getGameObject().getPosition(), targetProp);
            source.clearIsMoving();
        }
        else if (target.isMoving() && !source.isMoving()) {
            var offset = target.getOffsetAddedLastFrame();
            if (sourceWeight > targetWeight && (sourceWeight / targetWeight) >= 10) {
                target.clearOffsetAddedLastFrame();
                // if stopped element overweight moving object, invert the movement.
                applyMovement(offset, target.getGameObject().getPosition(), 1, -1);
                logger.info("applied inverse movement on target {0} from source {1}", target, source);
//                target.clearIsMoving();
                // Now it is moving in the opposite direction.
                return;
            }

            logger.info("target {0} vs source {1} - prop: {2}/{3} - offset: {4}", target, source, targetProp, sourceProp, offset);

            // Clear the offset because it was applied and we don't wan't to reapply if redetecting collisions in this frame.
            target.clearOffsetAddedLastFrame();
            applyMovement(offset, target.getGameObject().getPosition(), targetProp, source.getGameObject().getPosition(), sourceProp);
            target.clearIsMoving();
        }
        else if (source.isMoving() && target.isMoving()) {
            if ( sourceWeight > targetWeight && (sourceWeight / targetWeight) >= 10) {
                sourceProp = 1;
                targetProp = 0;
            }
            else if (targetWeight > sourceWeight && (targetWeight / sourceWeight) >= 10) {
                sourceProp = 0;
                targetProp = 1;
            }
            var sourceOffset = source.getOffsetAddedLastFrame();
            var targetOffset = target.getOffsetAddedLastFrame();

            logger.info("target {0} both source {1}: {2}/{3} - offset: s={4} t={5}", target, source, targetProp, sourceProp, sourceOffset, targetOffset);

            // Clear the offset because it was applied and we don't wan't to reapply if redetecting collisions in this frame.
            source.clearOffsetAddedLastFrame();
            target.clearOffsetAddedLastFrame();

            applyMovement(sourceOffset, source.getGameObject().getPosition(), sourceProp, target.getGameObject().getPosition(), targetProp);
            applyMovement(targetOffset, target.getGameObject().getPosition(), targetProp, source.getGameObject().getPosition(), sourceProp);

            source.clearIsMoving();
            target.clearIsMoving();
        }
        else {
            logger.warn("SOURCE {0} AND TARGET {1} DID'NT MOVED BUT ARE IN COLLISION!", source, target);
        }
    }

    private static void applyMovement(final Offset offset,
                                      final PositionComponent posA, final double proportionA,
                                      final PositionComponent posB, final double proportionB
    ) {
        var offsetA = calcProportionalOffset(offset, proportionA, 1);
        posB.addOffset(offsetA);

        var offsetB = calcProportionalOffset(offset, proportionB, -1);
        posA.addOffset(offsetB);
    }

    private static void applyMovement(final Offset offset, final PositionComponent pos, final double proportion, final int inverter) {
        var propOffset = calcProportionalOffset(offset, proportion, inverter);
        pos.addOffset(propOffset);
    }

    private static Offset calcProportionalOffset(final Offset offset, final double proportion, final int inverter) {
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
