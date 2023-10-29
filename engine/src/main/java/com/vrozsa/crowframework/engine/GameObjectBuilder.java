package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.game.ComposableGameObject;
import com.vrozsa.crowframework.game.component.Identifier;
import com.vrozsa.crowframework.game.component.PositionComponent;
import com.vrozsa.crowframework.game.component.StaticRenderer;
import com.vrozsa.crowframework.game.component.animation.AnimatedRenderer;
import com.vrozsa.crowframework.game.component.animation.AnimationTemplate;
import com.vrozsa.crowframework.game.component.audio.AudioPlayer;
import com.vrozsa.crowframework.game.component.camera.CameraFollower;
import com.vrozsa.crowframework.game.component.collider.AbstractCollisionHandler;
import com.vrozsa.crowframework.game.component.collider.ColliderGizmosRenderer;
import com.vrozsa.crowframework.game.component.collider.SquareCollider;
import com.vrozsa.crowframework.shared.api.game.CollisionHandler;
import com.vrozsa.crowframework.shared.api.game.Component;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Offsetable;
import com.vrozsa.crowframework.shared.api.screen.Sprite;
import com.vrozsa.crowframework.shared.api.audio.AudioClipPlayer;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.attributes.Vector;
import com.vrozsa.crowframework.shared.templates.SpriteTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Builder facility for GameObjects.
 */
public final class GameObjectBuilder  {
    private static final String POS_COMP_NAME = "position";
    private final List<Component> components;

    private PositionComponent position;

    private boolean isActive;

    private GameObjectBuilder(final Vector pos) {
        this(pos, true, POS_COMP_NAME);
    }

    private GameObjectBuilder(Vector pos, boolean isActive, String positionCompName) {
        this.isActive = isActive;
        components = new ArrayList<>();
        addPosition(pos, positionCompName);
    }

    public static GameObjectBuilder atOrigin() {
        return new GameObjectBuilder(Vector.origin());
    }

    public static GameObjectBuilder of(final int x, final int y, final int z) {
        return new GameObjectBuilder(Vector.of(x, y, z));
    }

    public static GameObjectBuilder of(final int x, final int y) {
        return new GameObjectBuilder(Vector.of(x, y, 0));
    }

    public static GameObjectBuilder of(final Vector pos) {
        return new GameObjectBuilder(pos);
    }

    private void addPosition(Vector pos, String positionCompName) {
        position = new PositionComponent(pos, positionCompName);
        components.add(position);
    }

    public GameObjectBuilder setActive(final boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    /**
     * Add a static renderer to the game object.
     * @param imageFile image to be rendered.
     * @param width image width.
     * @param height image height.
     * @return the builder object.
     */
    public GameObjectBuilder addStaticRenderer(String imageFile, int width, int height) {
        return addStaticRenderer(0, imageFile, width, height);
    }

    public GameObjectBuilder addStaticRenderer(int layer, String imageFile, int width, int height) {
        return addStaticRenderer(layer, imageFile, width, height, false);
    }

    public GameObjectBuilder addStaticRenderer(int layer, String imageFile, int width, int height, boolean alwaysRender) {
        var spriteTemplate = SpriteTemplate.builder()
                .imageFile(imageFile)
                .size(Size.of(width, height))
                .build();

        return addStaticRenderer(layer, spriteTemplate, false, false, alwaysRender);
    }

    public GameObjectBuilder addStaticRenderer(int layer, SpriteTemplate template) {
        return addStaticRenderer(layer, template, false, false, false);
    }

    public GameObjectBuilder addStaticRenderer(int layer, SpriteTemplate template, boolean flipX, boolean flipY, boolean alwaysRender) {
        var sprite = Sprite.of(template);
        var renderer = new StaticRenderer(position, layer, true, StaticRenderer.DEFAULT_STATIC_RENDERER, flipX, flipY, alwaysRender, sprite);
        components.add(renderer);

        return this;
    }

    public GameObjectBuilder addAnimatedRenderer(int layer, List<AnimationTemplate> templates) {
        return addAnimatedRenderer(layer, templates.toArray(new AnimationTemplate[0]));
    }

    /**
     * Adds an animated renderer component.
     * @param layer the renderer layer.
     * @param templates the animations templates data.
     * @return the builder.
     */
    public GameObjectBuilder addAnimatedRenderer(int layer, AnimationTemplate...templates) {
        var renderer = AnimatedRenderer.create(this.position, layer, AnimatedRenderer.DEFAULT_ANIMATED_RENDERER, false, false);

        for (var template : templates) {
            renderer.add(template.layer(), template);
        }

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

    public GameObjectBuilder addSquareCollider() {
        return addSquareCollider(0);
    }

    /**
     * Adds a new square collider component.
     * @param cooldown time to wait between triggering multiple detections (otherwise detection may happen for many frames and
     *                 trigger the handler multiple times if it doesn't implement a cooldown control).
     * @return the builder object.
     */
    public GameObjectBuilder addSquareCollider(final long cooldown) {
        var collider = new SquareCollider(cooldown);
        components.add(collider);
        return this;
    }

    /**
     * Adds a new square collider component.
     * @param cooldown time to wait before triggering multiple detections (otherwise detection may happen for many frames and
     *                 trigger the handler multiple times if it doesn't implement a cooldown control).
     * @param collisionLayer in which collision layer this component is.
     * @param collidesWith which other collision layers this component collides with.
     * @return the builder object.
     */
    public GameObjectBuilder addSquareCollider(final long cooldown, final String collisionLayer, final Set<String> collidesWith) {
        var collider = new SquareCollider(cooldown, collisionLayer, 0, collidesWith);
        components.add(collider);
        return this;
    }

    /**
     * Adds a new square collider component.
     * @param cooldown time to wait triggering multiple detections (otherwise detection may happen for many frames and
     *                 trigger the handler multiple times if it doesn't implement a cooldown control).
     * @param collisionLayer in which collision layer this component is.
     * @param collidesWith which other collision layers this component collides with.
     * @param rect the collision rect (if not specified, defaults to the sprite rect)
     * @return the builder object.
     */
    public GameObjectBuilder addSquareCollider(
            final long cooldown, final String collisionLayer,  final Set<String> collidesWith, final Rect rect) {
        var collider = new SquareCollider(cooldown, collisionLayer, 0, collidesWith, rect);
        components.add(collider);
        return this;
    }

    /**
     * Adds a new square collider component.
     * @param cooldown time to wait between collision detections (otherwise detection may happen for many frames and
     *                 trigger the handler multiple times if it doesn't implement a cooldown control).
     * @param collisionLayer in which collision layer this component is.
     * @param weight defines the weight of the object and how it will interact on collision.
     * @param collidesWith which other collision layers this component collides with.
     * @param rect the collision rect (if not specified, defaults to the sprite rect)
     * @return the builder object.
     */
    public GameObjectBuilder addSquareCollider(
            final long cooldown, final String collisionLayer, final int weight, final Set<String> collidesWith, final Rect rect) {
        var collider = new SquareCollider(cooldown, collisionLayer, weight, collidesWith, rect);
        components.add(collider);
        return this;
    }

    public GameObjectBuilder addCollisionHandler(final AbstractCollisionHandler collisionHandler) {
        components.add(collisionHandler);
        return this;
    }

    public GameObjectBuilder addCollisionHandler(final CollisionHandler handler) {
        var collisionHandler = new AbstractCollisionHandler() {
            @Override
            public void update() {
                // no op.
            }

            @Override
            protected void handle(GameObject source, GameObject target) {
                handler.handleCollision(source, target);
            }
        };

        components.add(collisionHandler);
        return this;
    }

    /**
     * Add gizmos to visually debug the collision boxes in the game.
     * @return the builder object.
     */
    public GameObjectBuilder addCollisionGizmos() {
        var colliderGizmos = new ColliderGizmosRenderer(position);
        components.add(colliderGizmos);
        return this;
    }

    /**
     * Add gizmos to visually debug the collision boxes in the game.
     * @param color (optional) custom gizmo color.
     * @return the builder object.
     */
    public GameObjectBuilder addCollisionGizmos(final Color color) {
        var colliderGizmos = new ColliderGizmosRenderer(position, color);
        components.add(colliderGizmos);
        return this;
    }

    /**
     * Add a camera follower component.
     * @param camera offsetable component of the renderer view.
     * @param offset offset added to the current object position (can be used to center the position in the screen).
     * @param followBox (optional) the boundaries in which the camera can follow the character. If out of the follow box
     *                  the camera will stop following.
     * @return the builder object.
     */
    public GameObjectBuilder addCameraFollower(final Offsetable camera, final Offset offset, final Rect followBox) {
        var cameraFollower = CameraFollower.create(position, camera, offset, followBox);
        components.add(cameraFollower);
        return this;
    }

    /**
     * Add a camera follower component.
     * @param camera offsetable component of the renderer view.
     * @param offset offset added to the current object position (can be used to center the position in the screen).
     * @return the builder object.
     */
    public GameObjectBuilder addCameraFollower(final Offsetable camera, final Offset offset) {
        return addCameraFollower(camera, offset, null);
    }

    /**
     * Adds the capability of playing audio clips in the game.
     * @param audioClipPlayer the audio clips player provided by the engine.
     * @return the builder object.
     */
    public GameObjectBuilder addAudioPlayer(final AudioClipPlayer audioClipPlayer) {
        var audioPlayer = AudioPlayer.create(audioClipPlayer);
        components.add(audioPlayer);
        return this;
    }

    public GameObjectBuilder addComponent(Component component) {
        components.add(component);
        return this;
    }

    public GameObjectBuilder addIdentifier(final String name) {
        var identifier = Identifier.create(name);
        components.add(identifier);
        return this;
    }

    public GameObjectBuilder addChild(PositionComponent child) {
        child.setParent(position);
        return this;
    }

    public GameObjectBuilder addChildren(List<GameObject> children) {
        children.stream()
                .map(GameObject::getPosition)
                .forEach(c -> c.setParent(position));
        return this;
    }

    public GameObjectBuilder addChildren(Iterable<PositionComponent> children) {
        children.forEach(c -> c.setParent(position));
        return this;
    }

    public GameObject build() {
        return ComposableGameObject.create(components, isActive);
    }
}
