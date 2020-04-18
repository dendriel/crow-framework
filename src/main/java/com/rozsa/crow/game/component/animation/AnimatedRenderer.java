package com.rozsa.crow.game.component.animation;

import com.rozsa.crow.game.component.Position;
import com.rozsa.crow.game.component.StaticRenderer;
import com.rozsa.crow.screen.api.Drawable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatedRenderer<TKey> extends StaticRenderer {
    public static final String DEFAULT_ANIMATED_RENDERER = "_defaultAnimatorComponent";
    private static final Integer DEFAULT_ANIMATIONS_LAYER = 0;

    private final Map<Integer, Map<TKey, Animation>> animationsLayers;
    private Integer animationLayer;

    public AnimatedRenderer(Position position, int layer, String name, boolean flipX, boolean flipY) {
        super(position, layer, name, flipX, flipY);

        animationsLayers = new HashMap<>();
        this.animationLayer = DEFAULT_ANIMATIONS_LAYER;
    }

    @Override
    public void update() {
        super.update();

        if (isDisabled()) {
            return;
        }

        Map<TKey, Animation> animations = getCurrentAnimations();
        animations.values().forEach(Animation::update);
        refreshDrawings();
    }

    protected Map<TKey, Animation> getCurrentAnimations() {
        return animationsLayers.get(animationLayer);
    }

    protected Map<TKey, Animation> getAnimations(int layer) {
        if (!animationsLayers.containsKey(layer)) {
            animationsLayers.put(layer, new HashMap<>());
        }
        return animationsLayers.get(layer);
    }

    protected Animation getAnimation(TKey key) {
        return getAnimation(key, animationLayer);
    }

    protected Animation getAnimation(TKey key, int layer) {
        Map<TKey, Animation> animations = getAnimations(layer);
        return animations.get(key);
    }

    private void refreshDrawings() {
        drawings.clear();
        Map<TKey, Animation> animations = getCurrentAnimations();
        for (Animation animation : animations.values()) {
            if (animation.isActive()) {
                drawings.addAll(animation.getDrawings(this));
            }
        }
    }

    @Override
    public List<Drawable> getDrawings(boolean filterInactive) {
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

    public void add(TKey key, Animation animation) {
        add(key, animation, animationLayer);
    }

    public void add(TKey key, Animation animation, int layer) {
        Map<TKey, Animation> animations = getAnimations(layer);
        animations.put(key, animation);
    }

    public void setActive(TKey key, boolean isActive) {
        setActive(key, isActive, animationLayer);
    }

    public void setActive(TKey key, boolean isActive, int layer) {
        Map<TKey, Animation> animations = getAnimations(layer);
        if (!animations.containsKey(key)) {
            return;
        }

        animations.get(key).setActive(isActive);
    }

    public void trigger(TKey key) {
        trigger(key, animationLayer);
    }

    public void trigger(TKey key, int layer) {
        Map<TKey, Animation> animations = getAnimations(layer);
        if (!animations.containsKey(key)) {
            return;
        }

        animations.get(key).trigger();
    }

    public boolean isAllAnimationsInactive() {
        return isAllAnimationsInactive(animationLayer);
    }

    public boolean isAllAnimationsInactive(int layer) {
        Map<TKey, Animation> animations = getAnimations(layer);
        return animations.values().stream().noneMatch(Animation::isActive);
    }

    public void setAllAnimationsInactive() {
        setAllAnimationsInactive(animationLayer);
    }

    public void setAllAnimationsInactive(int layer) {
        Map<TKey, Animation> animations = getAnimations(layer);
        animations.values().forEach(a -> a.setActive(false));
    }
}
