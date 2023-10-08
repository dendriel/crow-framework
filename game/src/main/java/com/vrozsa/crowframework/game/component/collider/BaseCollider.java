package com.vrozsa.crowframework.game.component.collider;

import com.vrozsa.crowframework.game.component.BaseComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderComponent;
import com.vrozsa.crowframework.shared.api.game.ColliderType;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Provides the template for creating specific colliders.
 */
abstract class BaseCollider extends BaseComponent implements ColliderComponent {
    protected static final String DEFAULT_COLLISION_LAYER = "DEFAULT_COLLISION_LAYER";
    /**
     * The collider position is the same position as the parent GameObject position. If its rect offset is set,
     * the collider position will be the parent position + its rect offset.
     *
     * The collider size is the same size as the parent GameObject renderer; If its rect size is set,
     * the collider size will override the renderer size.
     */
    protected Rect rect;
    protected boolean isActive;
    protected final String layer;
    protected final Set<String> collidesWith;
    protected final ColliderType type;

    protected BaseCollider(
            final ColliderType type,
            final String layer,
            final boolean isActive,
            final Set<String> collidesWith
    ) {
        this.type = type;
        this.layer = layer;

        this.isActive = isActive;
        this.collidesWith = new HashSet<>(collidesWith);
    }

    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public ColliderType getType() {
        return type;
    }

    public boolean canCollide() {
        return isEnabled() && !collidesWith.isEmpty();
    }

    public boolean cantCollide() {
        return isDisabled() || collidesWith.isEmpty();
    }

    public Rect getCollisionRect() {
        return Rect.of(getX(), getY(), getWidth(), getHeight());
    }

    public int getX() {
        if (Objects.isNull(rect)) {
            return getPosition().getX();
        }
        return getPosition().getX() + rect.getX();
    }

    public int getY() {
        if (Objects.isNull(rect)) {
            return getPosition().getY();
        }
        return getPosition().getY() + rect.getY();
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
}
