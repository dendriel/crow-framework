package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderType;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.game.PositionComponent;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
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
        var context = colliders.stream()
                .map(CollisionContext::new)
                .toList();

        final int maxHandles = 10;
        var handlesCount = new AtomicInteger();
        var collisionDetected = false;

        do {
            logger.debug(() -> handlesCount.get() > 0,"\nRedetecting collision. Round: {0}", handlesCount);
            collisionDetected = checkAndHandleCollision(context);
        } while (collisionDetected && handlesCount.incrementAndGet() <= maxHandles);
    }

    public boolean checkAndHandleCollision(final List<CollisionContext> colliders) {
        var isCollisionDetected = false;

        // Test each collider at beginning of the list with the next colliders of the list. Avoid retesting the same
        // collider combination.
        for (var i = 0; i < colliders.size(); i++) {
            var source = colliders.get(i);

            for (var j = i+1; j < colliders.size(); j++) {
                var target = colliders.get(j);

                var sourceOffset = source.offset();
                var targetOffset = target.offset();

                handleCollisionBetween(source, target);

                if (!sourceOffset.equals(source.offset()) ||
                        !targetOffset.equals(target.offset())) {
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
    private static void handleCollisionBetween(final CollisionContext source, final CollisionContext target) {
        var hasCollision = false;
        if (source.type() == ColliderType.SQUARE && source.type() == target.type()) {
            hasCollision = checkCollision(source, target);
        }

        if (!hasCollision) {
            return;
        }

        var sourceGo = source.getGameObject();
        var targetGo = target.getGameObject();

        var sourceCollisionHandler = source.getCollisionHandler();
        if (!Objects.isNull(sourceCollisionHandler) && source.canTriggerCollision() && source.canCollideWith(target)) {
            sourceCollisionHandler.handle(sourceGo, targetGo);
            source.signCollision();
        }

        var targetCollisionHandler = target.getCollisionHandler();
        if (!Objects.isNull(targetCollisionHandler) && target.canTriggerCollision() && target.canCollideWith(source)) {
            targetCollisionHandler.handle(targetGo, sourceGo);
            target.signCollision();
        }

        if (source.getGameObject().isActive() && target.getGameObject().isActive()) {
            handleInteraction(source, target);
        }
    }

    private static boolean checkCollision(final CollisionContext source, final CollisionContext target) {
        if (!source.canCollideWith(target) && !target.canCollideWith(source)) {
            return false;
        }

        var sourceRect = source.rect();
        var targetRect = target.rect();

        return sourceRect.getX() < targetRect.getX() + targetRect.getWidth() &&
            sourceRect.getX() + sourceRect.getWidth() > targetRect.getX() &&
            sourceRect.getY() < targetRect.getY() + targetRect.getHeight() &&
            sourceRect.getY() + sourceRect.getHeight() > targetRect.getY();
    }

    private static void handleInteraction(final CollisionContext source, final CollisionContext target) {
        if (source.weight() == 0 || target.weight() == 0) {
            return;
        }

        if (source.cantCollideWith(target) && target.cantCollideWith(source)) {
            return;
        }

        var totalWeight = source.weight() + (long)target.weight();
        var sourceProp = (double)source.weight() / totalWeight;
        var targetProp = (double)target.weight() / totalWeight;

        if (source.isMoving() && !target.isMoving()) {
            var offset = source.getOffsetAddedLastFrame();
            if (target.weight() > source.weight() && (target.weight() / source.weight()) >= 10) {
                source.clearOffsetAddedLastFrame();
                // if stopped element overweight moving object, invert the movement.
                applyMovement(offset, source.getGameObject().getPosition(), 0, -1);
                logger.info("applied inverse movement on srouce {0} from target {1}", source, target);
                // Don't clear isMoving flag. Now it is moving in the opposite direction.
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
            if (source.weight() > target.weight() && (source.weight() / target.weight()) >= 10) {
                target.clearOffsetAddedLastFrame();
                // if stopped element overweight moving object, invert the movement.
                applyMovement(offset, target.getGameObject().getPosition(), 1, -1);
                logger.info("applied inverse movement on target {0} from source {1}", target, source);
                // Don't clear isMoving flag. Now it is moving in the opposite direction.
                return;
            }

            logger.info("target {0} vs source {1} - prop: {2}/{3} - offset: {4}", target, source, targetProp, sourceProp, offset);

            // Clear the offset because it was applied and we don't wan't to reapply if redetecting collisions in this frame.
            target.clearOffsetAddedLastFrame();
            applyMovement(offset, target.getGameObject().getPosition(), targetProp, source.getGameObject().getPosition(), sourceProp);
            target.clearIsMoving();
        }
        else if (source.isMoving() && target.isMoving()) {
            if ( source.weight() > target.weight() && (source.weight() / target.weight()) >= 10) {
                sourceProp = 1;
                targetProp = 0;
            }
            else if (target.weight() > source.weight() && (target.weight() / source.weight()) >= 10) {
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

    /**
     * Keeps information about detected collisions from the collider.
     */
    private static class CollisionContext {
        private final ColliderComponent collider;
        private final PositionComponent position;

        CollisionContext(ColliderComponent collider) {
            this.collider = collider;
            this.position = collider.getGameObject().getPosition();
        }

        Offset offset() {
            return position.getOffset();
        }

        ColliderType type() {
            return collider.getType();
        }

        int weight() {
            return collider.getWeight();
        }

        boolean canCollideWith(CollisionContext target) {
            return collider.canCollideWith(target.collider);
        }

        boolean cantCollideWith(CollisionContext target) {
            return collider.cantCollideWith(target.collider);
        }

        Rect rect() {
            return collider.getCollisionRect();
        }

        AbstractCollisionHandler getCollisionHandler() {
            return collider.getGameObject().getComponent(AbstractCollisionHandler.class);
        }

        GameObject getGameObject() {
            return collider.getGameObject();
        }

        boolean canTriggerCollision() {
            return collider.canTriggerCollision();
        }

        void signCollision() {
            collider.signCollision();
        }

        boolean isMoving() {
            return collider.isMoving();
        }

        void clearIsMoving() {
            collider.clearIsMoving();;
        }

        Offset getOffsetAddedLastFrame() {
            return collider.getOffsetAddedLastFrame();
        }

        void clearOffsetAddedLastFrame() {
            collider.clearOffsetAddedLastFrame();
        }
    }
}
