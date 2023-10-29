package com.vrozsa.crowframework.game;

import com.vrozsa.crowframework.game.component.PositionComponent;
import com.vrozsa.crowframework.shared.api.game.Component;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.game.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Game Object representation and container of components.
 * <p>
 *     It always have a base position component and a container to receive others components.
 * </p>
 */
public final class ComposableGameObject implements GameObject {
    public final PositionComponent position;
    private final List<Component> components;
    private boolean isActive;

    private ComposableGameObject(final List<Component> components, final boolean isActive) {
        this.isActive = isActive;
        this.components = new ArrayList<>(components);
        position = (PositionComponent) components.stream()
                .filter(PositionComponent.class::isInstance)
                .findFirst()
                .orElse(null);

        assert position != null : "GameObject has no assigned Position!";

        this.components.forEach(c -> c.setGameObject(this));
        this.components.forEach(Component::wrapUp);
    }

    public static ComposableGameObject create(final List<Component> components, final boolean isActive) {
        return new ComposableGameObject(components, isActive);
    }

    public void earlyUpdate() {
        components.forEach(Component::earlyUpdate);
        position.getChildren().forEach(p -> p.getGameObject().earlyUpdate());
    }

    public void update() {
        components.forEach(Component::update);
        position.getChildren().forEach(p -> p.getGameObject().update());
    }

    public void lateUpdate() {
        components.forEach(Component::lateUpdate);
        position.getChildren().forEach(p -> p.getGameObject().lateUpdate());
    }

    public Position getPosition() {
        return position;
    }

    /**
     * Adds a new component into the game-object components container.
     * <p>
     *     It also sets the parent game-object from the component and calls its wrapUp() method.
     * </p>
     * @param component the component to be added.
     */
    public void addComponent(final Component component) {
        components.add(component);
        component.setGameObject(this);
        component.wrapUp();
    }

    public <T extends Component> T getComponent(Class<T> type, String name) {
        var targetComponent = components.stream()
                .filter(c -> (c.getClass().equals(type) || type.isInstance(c)) && c.getName().equals(name))
                .findFirst()
                .orElse(null);

        return targetComponent != null ? type.cast(targetComponent) : null;
    }

    public <T> T getComponent(final Class<T> type) {
        var component = components.stream()
                .filter(c -> c.getClass().equals(type) || type.isInstance(c))
                .findFirst()
                .orElse(null);

        return component != null ? type.cast(component) : null;
    }

    public <T> boolean hasComponent(final Class<T> type) {
        return components.stream()
                .anyMatch(c -> c.getClass().equals(type) || type.isInstance(c));
    }

    public <T extends Component> T getComponentFromChildren(Class<T> type) {
        List<Position> children = position.getChildren();
        for (Position pos : children) {
            T currComp = pos.getGameObject().getComponent(type);
            if (currComp != null) {
                return currComp;
            }

            T childrenComp = pos.getGameObject().getComponentFromChildren(type);
            if (childrenComp != null) {
                return childrenComp;
            }
        }

        return null;
    }

    public <T extends Component> List<T> getComponentsFromChildren(Class<T> type) {
        var targetComponents = new ArrayList<T>();
        List<Position> children = position.getChildren();

        for (Position pos : children) {
            T currComp = pos.getGameObject().getComponent(type);
            if (currComp != null) {
                targetComponents.add(currComp);
            }

            List<T> childrenComp = pos.getGameObject().getComponentsFromChildren(type);
            targetComponents.addAll(childrenComp);
        }

        return targetComponents;
    }

    public <T> List<T> getAllComponents(final Class<T> type) {
        var targetComponents = components.stream()
                .filter(c -> c.getClass().equals(type) || type.isInstance(c))
                .toList();

        var castedComponents = new ArrayList<T>();
        targetComponents.forEach(c -> castedComponents.add(type.cast(c)));

        return castedComponents;
    }

    public <T extends Component> boolean containsComponent(Class<T> type) {
        return components.stream()
                .anyMatch(c -> c.getClass().equals(type) || type.isInstance(c));
    }

    public void wrapUp() {
        components.forEach(Component::wrapUp);
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isInactive() {
        return !isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
