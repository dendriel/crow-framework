package com.vrozsa.crowframework.game.component.animation;

import com.vrozsa.crowframework.game.component.Position;
import com.vrozsa.crowframework.game.component.StaticRenderer;
import com.vrozsa.crowframework.shared.api.screen.Drawable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Handles animations from a game object.
 *
 * A AnimatedRenderer allows to configure animations by 'layers'. We may have animations like idle, walking, attacking,
 * but layers allows us to create a layer with 'default animations'; then have another layer in which there also have idle,
 * walking, attacking, but game object is in a 'overpowered' state and then the animations represents this state.
 *
 * This way, the 'layer' allows us to switch animation bundles transparently, keeping the same animation keys.
 */
public class AnimatedRenderer extends StaticRenderer {
    public static final String DEFAULT_ANIMATED_RENDERER = "_defaultAnimatorComponent";
    private static final Integer DEFAULT_ANIMATIONS_LAYER = 0;

    private final Map<Integer, Map<String, Animation>> animationsLayers;
    private Integer animationLayer;

    public AnimatedRenderer(Position position, int layer, String name, boolean flipX, boolean flipY) {
        super(position, layer, name, flipX, flipY);

        animationsLayers = new HashMap<>();
        animationLayer = DEFAULT_ANIMATIONS_LAYER;
    }

    @Override
    public void update() {
        super.update();

        if (isDisabled()) {
            return;
        }

        getCurrentAnimations().values().forEach(Animation::update);
    }

    protected Map<String, Animation> getCurrentAnimations() {
        return getAnimations(animationLayer);
    }

    /**
     * Get all animations by its layer id.
     * @param layer the target animations layer.
     * @return the animations of the animation layer. Will create a new entry if the layer is absent.
     */
    protected Map<String, Animation> getAnimations(int layer) {
        animationsLayers.computeIfAbsent(layer, entry -> new HashMap<>());
        return animationsLayers.get(layer);
    }

    public Animation getAnimation(String key) {
        return getAnimation(key, animationLayer);
    }

    protected Animation getAnimation(String key, int layer) {
        var animations = getAnimations(layer);
        return animations.get(key);
    }

    private void refreshDrawings() {
        drawings.clear();
        var animations = getCurrentAnimations();
        for (var animation : animations.values()) {
            if (animation.isActive()) {
                drawings.addAll(animation.getDrawings(this));
            }
        }
    }

    @Override
    public List<Drawable> getDrawings(boolean filterInactive) {
        refreshDrawings();
        return drawings;
    }

    public void setAnimationLayer(int layer) {
        if (animationsLayers.containsKey(layer)) {
            this.animationLayer = layer;
        }
    }

    public int getAnimationLayer() {
        return animationLayer;
    }

    public void add(String key, Animation animation) {
        add(key, animation, animationLayer);
    }

    public void add(String key, Animation animation, int layer) {
        getAnimations(layer).put(key, animation);
    }

    /**
     * Enable the target animation from the current layer.
     * @param key target animation key
     */
    public void setOnlyEnabled(final String key) {
        setAllAnimationsInactive(key);
        setActive(key, true, animationLayer);
    }

    /**
     * Set the active state for the target animation.
     * @param key key from animation.
     * @param isActive true=active; false=inactive.
     */
    public void setActive(String key, boolean isActive) {
        setActive(key, isActive, animationLayer);
    }

    public void setActive(String key, boolean isActive, int layer) {
        var animations = getAnimations(layer);
        if (animations.containsKey(key)) {
            animations.get(key).setActive(isActive);
        }
    }

    public void trigger(String key) {
        trigger(key, animationLayer);
    }

    public void trigger(String key, int layer) {
        var animations = getAnimations(layer);
        if (animations.containsKey(key)) {
            animations.get(key).trigger();
        }
    }

    public boolean isAnimationActive(String key) {
        var animation = getCurrentAnimations()
                .get(key);

        return animation == null || animation.isActive();
    }

    public boolean isLastAnimationFrame(String key) {
        var animation = getCurrentAnimations()
                .get(key);

        return animation == null || animation.isLastFrame();
    }

    public boolean isAllAnimationsInactive() {
        return isAllAnimationsInactive(animationLayer);
    }

    public boolean isAllAnimationsInactive(int layer) {
        return getAnimations(layer).values().stream().noneMatch(Animation::isActive);
    }

    public void setAllAnimationsInactive() {
        setAllAnimationsInactive("");
    }

    public void setAllAnimationsInactive(String exceptionKey) {
        setAllAnimationsInactive(animationLayer, exceptionKey);
    }

    public void setAllAnimationsInactive(int layer, String exceptionKey) {
        var animations = getAnimations(layer);

        for (var entry : animations.entrySet()) {
            if (!entry.getKey().equals(exceptionKey)) {
                entry.getValue().setActive(false);
            }
        }

//        getAnimations(layer).values().stream()
//                .filter(anim -> anim.)
//                .forEach(a -> a.setActive(false));
    }
}
