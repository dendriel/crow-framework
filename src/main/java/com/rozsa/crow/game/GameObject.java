package com.rozsa.crow.game;

import com.rozsa.crow.game.api.Component;
import com.rozsa.crow.game.attributes.Vector;
import com.rozsa.crow.game.component.Position;
import com.rozsa.crow.game.component.StaticRenderer;
import com.rozsa.crow.screen.sprite.Sprite;
import com.rozsa.crow.screen.sprite.SpriteTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class GameObject {
    public final Position position;

    private boolean isActive;

    private List<Component> components;

    private GameObject(Builder builder) {
        isActive = builder.isActive;
        components = builder.components;
        components.forEach(c -> {
            c.setGameObject(this);
            c.wrapUp();
        });

        position = (Position) components.stream()
                .filter(c -> c instanceof Position)
                .findFirst()
                .orElse(null);

        assert position != null : String.format("GameObject has no assigned Position!");
    }

    public void update() {
        components.forEach(Component::update);
    }

    public void lateUpdate() {
        components.forEach(Component::lateUpdate);
    }

    public Position getPosition() {
        return position;
    }

    public StaticRenderer getRenderer() {
        return getComponent(StaticRenderer.class);
    }

    public void addComponent(Component component) {
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

    public <T extends Component> T getComponent(Class<T> kind) {
        Component component = components.stream()
                .filter(c -> c.getClass().equals(kind) || kind.isInstance(c))
                .findFirst()
                .orElse(null);

        return component != null ? kind.cast(component) : null;
    }

    public <T extends Component> List<T> getComponentsFromChildren(Class<T> kind) {
        List<T> components = new ArrayList<>();
        List<Position> children = position.getChildren();

        for (Position pos : children) {
            T currComp = pos.getGameObject().getComponent(kind);
            components.add(currComp);

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

    public static class Builder {
        protected List<Component> components;

        protected Position position;

        private boolean isActive;

        public Builder(Vector pos, boolean isActive) {
            this(pos, isActive, "unnamed");
        }

        public Builder(Vector pos) {
            this(pos, "unnamed");
        }

        public Builder(Vector pos, String positionCompName) {
            this(pos, true, positionCompName);
        }

        public Builder(Vector pos, boolean isActive, String positionCompName) {
            this.isActive = isActive;
            components = new ArrayList<>();
            addPosition(pos, positionCompName);
        }

        private void addPosition(Vector pos, String positionCompName) {
            position = new Position(pos, positionCompName);
            components.add(position);
        }

        public Builder addStaticRenderer(int layer) {
            StaticRenderer renderer = new StaticRenderer(position, layer, StaticRenderer.DEFAULT_STATIC_RENDERER, false, false);
            components.add(renderer);
            return this;
        }

        public Builder addStaticRenderer(int layer, SpriteTemplate spriteData) {
            return addStaticRenderer(layer, spriteData, false, false);
        }

        public Builder addStaticRenderer(int layer, SpriteTemplate spriteData, boolean flipX, boolean flipY) {
            Sprite sprite = new Sprite(spriteData);
            StaticRenderer renderer = new StaticRenderer(position, layer, StaticRenderer.DEFAULT_STATIC_RENDERER, flipX, flipY, sprite);
            components.add(renderer);

            return this;
        }

        public Builder addStaticRenderer(int layer, boolean flipX, boolean flipY, List<SpriteTemplate> spritesData) {
            List<Sprite> sprites = spritesData.stream().map(Sprite::new).collect(Collectors.toList());
            StaticRenderer renderer = new StaticRenderer(position, layer, StaticRenderer.DEFAULT_STATIC_RENDERER, flipX, flipY, sprites.toArray(new Sprite[0]));
            components.add(renderer);

            return this;
        }

        public Builder addChild(Position child) {
            child.setParent(position);
            return this;
        }

        public Builder addChildren(List<Position> children) {
            children.forEach(c -> c.setParent(position));
            return this;
        }

        public GameObject build() {
            return new GameObject(this);
        }
    }
}
