package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderType;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.game.Position;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handles collision between game objects.
 */
public final class CollisionDetector {
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
            var context = colliders.stream()
                    .map(CollisionContext::new)
                    .toList();

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

        var sourceStatus = source.getCollisionStatusFor(target);
        var targetStatus = target.getCollisionStatusFor(source);

        var totalWeight = source.weight() + (long)target.weight();
        var sourceProp = (double)source.weight() / totalWeight;
        var targetProp = (double)target.weight() / totalWeight;

        if (sourceStatus.isMoving() && !targetStatus.isMoving()) {
            var offset = sourceStatus.getOffsetAddedLastFrame();
            if (target.weight() > source.weight() && (target.weight() / source.weight()) >= 10) {
                sourceStatus.clearOffsetAddedLastFrame();
                // if stopped element overweight moving object, invert the movement.
                applyReverseMovement(offset, source.getGameObject().getPosition(), 1, sourceStatus); // this was 0, why?
                logger.info("applied inverse movement on source {0} from target {1} - source offset: {2}", source, target, offset);
                // Don't clear isMoving flag. Now it is moving in the opposite direction.
                return;
            }

            logger.info("source {0} vs target {1} - prop: {2}/{3} - offset: {4}", source, target, sourceProp, targetProp, offset);

            // Clear the offset because it was applied and we don't wan't to reapply if redetecting collisions in this frame.
            sourceStatus.clearOffsetAddedLastFrame();
            applyMovement(offset, source.getGameObject().getPosition(), sourceProp, sourceStatus, target.getGameObject().getPosition(), targetProp, targetStatus);
            sourceStatus.clearIsMoving();
        }
        else if (targetStatus.isMoving() && !sourceStatus.isMoving()) {
            var offset = targetStatus.getOffsetAddedLastFrame();
            if (source.weight() > target.weight() && (source.weight() / target.weight()) >= 10) {
                targetStatus.clearOffsetAddedLastFrame();
                target.collider.clearOffsetAddedLastFrame();
                // if stopped element overweight moving object, invert the movement.
                applyReverseMovement(offset, target.getGameObject().getPosition(), 1, targetStatus);
                logger.info("applied inverse movement on target {0} from source {1} - target offset: {2}", target, source, offset);
                // Don't clear isMoving flag. Now it is moving in the opposite direction.
                return;
            }

            logger.info("target {0} vs source {1} - prop: {2}/{3} - offset: {4}", target, source, targetProp, sourceProp, offset);

            // Clear the offset because it was applied and we don't wan't to reapply if redetecting collisions in this frame.
            targetStatus.clearOffsetAddedLastFrame();
            applyMovement(offset, target.getGameObject().getPosition(), targetProp, targetStatus, source.getGameObject().getPosition(), sourceProp, sourceStatus);
            targetStatus.clearIsMoving();
        }
        else if (sourceStatus.isMoving() && targetStatus.isMoving()) {
            if ( source.weight() > target.weight() && (source.weight() / target.weight()) >= 10) {
                sourceProp = 1;
                targetProp = 0;
            }
            else if (target.weight() > source.weight() && (target.weight() / source.weight()) >= 10) {
                sourceProp = 0;
                targetProp = 1;
            }
            var sourceOffset = sourceStatus.getOffsetAddedLastFrame();
            var targetOffset = targetStatus.getOffsetAddedLastFrame();

            logger.info("source {0} both target {1} : {2}/{3} - offset: s={4} t={5}", source, target, sourceProp, targetProp, sourceOffset, targetOffset);

            // Clear the offset because it was applied and we don't wan't to reapply if redetecting collisions in this frame.
            sourceStatus.clearOffsetAddedLastFrame();
            targetStatus.clearOffsetAddedLastFrame();

            applyMovement(sourceOffset, source.getGameObject().getPosition(), sourceProp, sourceStatus, target.getGameObject().getPosition(), targetProp, targetStatus);
            applyMovement(targetOffset, target.getGameObject().getPosition(), targetProp, targetStatus, source.getGameObject().getPosition(), sourceProp, sourceStatus);

            sourceStatus.clearIsMoving();
            targetStatus.clearIsMoving();
        }
        else {
            logger.warn("SOURCE {0} AND TARGET {1} DID'NT MOVE BUT ARE IN COLLISION!", source, target);
        }
    }

    private static void applyMovement(final Offset offset,
                                      final Position posA, final double proportionA, CollisionStatus sourceStatus,
                                      final Position posB, final double proportionB, CollisionStatus targetStatus
    ) {
        var offsetA = calcProportionalOffset(offset, proportionA, 1);
        posB.addOffset(offsetA);
        targetStatus.addOffset(offsetA);
        targetStatus.setMoving(!offsetA.atOrigin());

        var offsetB = calcProportionalOffset(offset, proportionB, -1);
        posA.addOffset(offsetB);
        sourceStatus.addOffset(offsetB);
        sourceStatus.setMoving(!offsetB.atOrigin());
    }

    private static void applyReverseMovement(final Offset offset, final Position pos, final double proportion, CollisionStatus status) {
        var propOffset = calcProportionalOffset(offset, proportion, -1);
        pos.addOffset(propOffset);
        status.addOffset(propOffset);
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
    private static class CollisionContext implements Comparable<CollisionContext> {
        private final ColliderComponent collider;
        private final Position position;
        private final Map<CollisionContext, CollisionStatus> collisionStatus;

        CollisionContext(ColliderComponent collider) {
            this.collider = collider;
            this.position = collider.getGameObject().getPosition();

            collisionStatus = new HashMap<>();
        }

        CollisionStatus getCollisionStatusFor(CollisionContext target) {
            return collisionStatus.computeIfAbsent(target, this::initializeCollisionStatus);
        }

        CollisionStatus initializeCollisionStatus(CollisionContext target) {
            logger.info("Creating collision status from source={0} to target={1}", this, target);
            // If originally moving, return original status.
            if (collider.isMoving()) {
                return new CollisionStatus(collider);
            }

            // If not originally moving, check if started moving by another collider.
            for (var entry : collisionStatus.values()) {
                if (entry.isMoving) {
                    // If moved by another collider, copy its status.
                    return new CollisionStatus(entry);
                }
            }

            return new CollisionStatus(collider);
        }

        boolean startedMoving() {
            if (collider.isMoving()) {
                // already moving.
                return false;
            }

            for (var entry : collisionStatus.values()) {
                if (entry.isMoving) {
                    return true;
                }
            }

            return false;
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

        @Override
        public String toString() {
            return collider.toString();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public int compareTo(CollisionContext o) {
            if (this.equals(o)) {
                return 0;
            }

            if (this.position.getAbsolutePosX() > o.position.getAbsolutePosX()) {
                return 1;
            }
            else if (this.position.getAbsolutePosX() < o.position.getAbsolutePosX()) {
                return -1;
            }
            if (this.position.getAbsolutePosY() > o.position.getAbsolutePosY()) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }

    private static class CollisionStatus {
        private boolean isMoving;
        private Offset offsetAddedLastFrame;

        CollisionStatus(ColliderComponent collider) {
            isMoving = collider.isMoving();
            offsetAddedLastFrame = collider.getOffsetAddedLastFrame();
        }

        CollisionStatus(CollisionStatus status) {
            isMoving = status.isMoving;
            offsetAddedLastFrame = status.offsetAddedLastFrame;
        }

        boolean isMoving() {
            return isMoving;
        }

        void clearIsMoving() {
            isMoving = false;
        }

        void setMoving(boolean isMoving) {
            this.isMoving = isMoving;
        }

        Offset getOffsetAddedLastFrame() {
            return offsetAddedLastFrame;
        }

        void clearOffsetAddedLastFrame() {
            offsetAddedLastFrame = Offset.origin();
        }

        void addOffset(Offset offset) {
            offsetAddedLastFrame = offsetAddedLastFrame.sum(offset);
        }
    }
}
