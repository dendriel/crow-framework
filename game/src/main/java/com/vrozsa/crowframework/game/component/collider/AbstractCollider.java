package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderType;
import com.vrozsa.crowframework.shared.api.game.Position;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.time.Cooldown;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Provides the template for creating specific colliders.
 */
abstract class AbstractCollider extends AbstractComponent implements ColliderComponent {
    protected static final String DEFAULT_COLLISION_LAYER = "DEFAULT_COLLISION_LAYER";
    protected static final long DEFAULT_COOLDOWN_MILLIS = 0;

    /**
     * The collider position is the same position as the parent GameObject position. If its rect offset is set,
     * the collider position will be the parent position + its rect offset.
     *
     * The collider size is the same size as the parent GameObject renderer; If its rect size is set,
     * the collider size will override the renderer size.
     */
    protected Rect rect;
    private Position position;

    protected boolean isActive;
    protected int weight;
    protected final String layer;
    protected final Set<String> collidesWith;
    protected final ColliderType type;
    protected final Cooldown cooldown;
    protected Offset offsetAddedLastFrame;
    protected boolean isOffsetAddedLastFrame;

    protected AbstractCollider(
            final ColliderType type,
            final String layer,
            final boolean isActive,
            final int weight,
            final Set<String> collidesWith,
            final Cooldown cooldown,
            final Rect rect
    ) {
        this.type = type;
        this.layer = layer;
        this.isActive = isActive;
        this.weight = weight;
        this.collidesWith = new HashSet<>(collidesWith);
        this.cooldown = cooldown;
        this.rect = rect;
        offsetAddedLastFrame = Offset.origin();
    }

    @Override
    public void wrapUp() {
        super.wrapUp();

        position = getPosition();
        assert position != null : "AbstractCollider requires a PositionComponent";

        position.addPositionOffsetAddedListener(this::onOffsetAdded);
    }

    @Override
    public void earlyUpdate() {
        super.earlyUpdate();
        // reset this every update, before the GO handling phase in which it can be set again.
        isOffsetAddedLastFrame = false;
        offsetAddedLastFrame = Offset.origin();
    }

    @Override
    public void update() {
        // no op.
    }

    private void onOffsetAdded(int offsetX, int offsetY) {
        offsetAddedLastFrame = offsetAddedLastFrame.sum(Offset.of(offsetX, offsetY));

        // the offset may be added in the game object update phase, and it is to be consumed by the collision handling
        // before the next go update phase.
        isOffsetAddedLastFrame = true;
    }

    public boolean isMoving() {
        return isOffsetAddedLastFrame;
    }

    public Offset getOffsetAddedLastFrame() {
        return offsetAddedLastFrame;
    }

    public void clearOffsetAddedLastFrame() {
        isOffsetAddedLastFrame = false;
        offsetAddedLastFrame = Offset.origin();
    }

    public void clearIsMoving() {
        isOffsetAddedLastFrame = false;
    }

    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void signCollision() {
        cooldown.start();
    }

    public ColliderType getType() {
        return type;
    }

    public String getLayer() {
        return layer;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    public Set<String> getCollisionLayers() {
        return Set.copyOf(collidesWith);
    }

    public boolean canTriggerCollision() {
        return isEnabled() && cooldown.isFinished();
    }

    public boolean canCollideWith(final ColliderComponent target) {
        return collidesWith.contains(target.getLayer());
    }

    public boolean cantCollideWith(final ColliderComponent target) {
        return !canCollideWith(target);
    }

    public boolean cantCollide() {
        return isDisabled() || collidesWith.isEmpty() || cooldown.isWaiting();
    }

    public Rect getRect() {
        if (Objects.isNull(rect)) {
            return Rect.atOrigin(getWidth(), getHeight());
        }

        return rect.clone();
    }

    public Rect getCollisionRect() {
        return Rect.of(getX(), getY(), getWidth(), getHeight());
    }

    public int getX() {
        if (Objects.isNull(rect)) {
            return position.getAbsolutePosX();
        }
        return position.getAbsolutePosX() + rect.getX();
    }

    public int getY() {
        if (Objects.isNull(rect)) {
            return position.getAbsolutePosY();
        }
        return position.getAbsolutePosY() + rect.getY();
    }

    public int getWidth() {
        if (Objects.isNull(rect)) {
            return getRendererSize().getWidth();
        }

        return rect.getWidth();
    }

    public int getHeight() {
        if (Objects.isNull(rect)) {
            return getRendererSize().getHeight();
        }

        return rect.getHeight();
    }

    private Size getRendererSize() {
        var renderer = getComponent(Renderer.class);
        if (Objects.isNull(renderer)) {
            return Size.zeroed();
        }

        return renderer.getSize();
    }

    @Override
    public String toString() {
        return String.format("[layer=%s rect=%s]", layer, rect);
    }
}
