package com.vrozsa.crowframework.game;

import com.vrozsa.crowframework.shared.api.game.Component;
import com.vrozsa.crowframework.game.component.Position;
import com.vrozsa.crowframework.game.component.StaticRenderer;
import com.vrozsa.crowframework.shared.api.game.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ComposableGameObject implements GameObject {
    public final Position position;
    private final List<Component> components;

    private boolean isActive;

    public ComposableGameObject(final List<Component> components, final boolean isActive) {
        this.isActive = isActive;
        this.components = new ArrayList<>(components);
        position = (Position) components.stream()
                .filter(Position.class::isInstance)
                .findFirst()
                .orElse(null);

        assert position != null : "GameObject has no assigned Position!";

        this.components.forEach(c -> {
            c.setGameObject(this);
            c.wrapUp();
        });
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

    public StaticRenderer getRenderer() {
        return getComponent(StaticRenderer.class);
    }

    public void addComponent(final Component component) {
        components.add(component);
        component.setGameObject(this);
        component.wrapUp();
    }

    public <T extends Component> T getComponent(Class<T> kind, String name) {
        Component component = components.stream()
                .filter(c -> (c.getClass().equals(kind) || kind.isInstance(c)) && c.getName().equals(name))
                .findFirst()
                .orElse(null);

        return component != null ? kind.cast(component) : null;
    }

    public <T> T getComponent(final Class<T> kind) {
        var component = components.stream()
                .filter(c -> c.getClass().equals(kind) || kind.isInstance(c))
                .findFirst()
                .orElse(null);

        return component != null ? kind.cast(component) : null;
    }

    public <T> boolean hasComponent(final Class<T> kind) {
        return components.stream()
                .anyMatch(c -> c.getClass().equals(kind) || kind.isInstance(c));
    }

    public <T extends Component> T getComponentFromChildren(Class<T> kind) {
        List<Position> children = position.getChildren();
        for (Position pos : children) {
            T currComp = pos.getGameObject().getComponent(kind);
            if (currComp != null) {
                return currComp;
            }

            T childrenComp = pos.getGameObject().getComponentFromChildren(kind);
            if (childrenComp != null) {
                return childrenComp;
            }
        }

        return null;
    }

    public <T extends Component> List<T> getComponentsFromChildren(Class<T> kind) {
        List<T> components = new ArrayList<>();
        List<Position> children = position.getChildren();

        for (Position pos : children) {
            T currComp = pos.getGameObject().getComponent(kind);
            if (currComp != null) {
                components.add(currComp);
            }

            List<T> childrenComp = pos.getGameObject().getComponentsFromChildren(kind);
            components.addAll(childrenComp);
        }

        return components;
    }

    public <T extends Component> List<T> getAllComponents(Class<T> kind) {
        List<Component> targetComponents = components.stream()
                .filter(c -> c.getClass().equals(kind) || kind.isInstance(c))
                .collect(Collectors.toList());

        List<T> castedComponents = new ArrayList<>();
        targetComponents.forEach(c -> castedComponents.add(kind.cast(c)));

        return castedComponents;
    }

    public <T> T getService(Class<T> kind) {
        Component component = components.stream()
                .filter(c -> c.getClass().equals(kind) || kind.isInstance(c))
                .findFirst()
                .orElse(null);

        return component != null ? kind.cast(component) : null;
    }

    public <T> List<T> getAllServices(Class<T> kind) {
        List<Component> targetComponents = components.stream()
                .filter(c -> c.getClass().equals(kind) || kind.isInstance(c))
                .collect(Collectors.toList());

        List<T> castedComponents = new ArrayList<>();
        targetComponents.forEach(c -> castedComponents.add(kind.cast(c)));

        return castedComponents;
    }

    public <T> List<T> getServicesFromChildren(Class<T> kind) {
        List<T> components = new ArrayList<>();
        List<Position> children = position.getChildren();

        for (Position pos : children) {
            T currComp = pos.getGameObject().getService(kind);
            components.add(currComp);

            List<T> childrenComp = pos.getGameObject().getServicesFromChildren(kind);
            components.addAll(childrenComp);
        }

        return components;
    }

    public <T extends Component> boolean containsComponent(Class<T> kind) {
        return getComponent(kind) != null;
    }

    public void wrapUp() {
        components.forEach(c -> c.wrapUp());
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isInactive() {
        return !isActive;
    }
}
