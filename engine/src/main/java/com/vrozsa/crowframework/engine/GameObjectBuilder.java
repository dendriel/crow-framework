package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.game.ComposableGameObject;
import com.vrozsa.crowframework.game.component.Position;
import com.vrozsa.crowframework.game.component.StaticRenderer;
import com.vrozsa.crowframework.shared.api.game.Component;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Sprite;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.attributes.Vector;
import com.vrozsa.crowframework.shared.templates.SpriteTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder facility for GameObjects.
 */
public final class GameObjectBuilder  {
    private static final String POS_COMP_NAME = "position";
    private final List<Component> components;

    private Position position;

    private boolean isActive;

    private GameObjectBuilder(final Vector pos) {
        this(pos, true, POS_COMP_NAME);
    }

    private GameObjectBuilder(Vector pos, boolean isActive, String positionCompName) {
        this.isActive = isActive;
        components = new ArrayList<>();
        addPosition(pos, positionCompName);
    }

    public static GameObjectBuilder of(final int x, final int y, final int z) {
        return new GameObjectBuilder(Vector.of(x, y, z));
    }

    public static GameObjectBuilder of(final Vector pos) {
        return new GameObjectBuilder(pos);
    }

    private void addPosition(Vector pos, String positionCompName) {
        position = new Position(pos, positionCompName);
        components.add(position);
    }

    public GameObjectBuilder setActive(final boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public GameObjectBuilder addStaticRenderer(String imageFile, int width, int height) {
        return addStaticRenderer(0, imageFile, width, height);
    }

    public GameObjectBuilder addStaticRenderer(int layer, String imageFile, int width, int height) {
        var spriteTemplate = SpriteTemplate.builder()
                .imageFile(imageFile)
                .size(Size.of(width, height))
                .build();

        return addStaticRenderer(layer, spriteTemplate, false, false);
    }

    public GameObjectBuilder addStaticRenderer(int layer, SpriteTemplate template) {
        return addStaticRenderer(layer, template, false, false);
    }

    public GameObjectBuilder addStaticRenderer(int layer, SpriteTemplate template, boolean flipX, boolean flipY) {
        var sprite = Sprite.of(template);
        var renderer = new StaticRenderer(position, layer, StaticRenderer.DEFAULT_STATIC_RENDERER, flipX, flipY, sprite);
        components.add(renderer);

        return this;
    }

    public GameObjectBuilder addStaticRenderer(int layer, boolean flipX, boolean flipY, List<SpriteTemplate> spritesTemplates) {
        List<Sprite> sprites = spritesTemplates.stream()
                .map(Sprite::of)
                .toList();
        var renderer =
                new StaticRenderer(position, layer, StaticRenderer.DEFAULT_STATIC_RENDERER, flipX, flipY, sprites.toArray(new Sprite[0]));
        components.add(renderer);

        return this;
    }

    public GameObjectBuilder addChild(Position child) {
        child.setParent(position);
        return this;
    }

    public GameObjectBuilder addChildren(Iterable<Position> children) {
        children.forEach(c -> c.setParent(position));
        return this;
    }

    public GameObject build() {
        return new ComposableGameObject(components, isActive);
    }
}
