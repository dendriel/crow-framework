package com.rozsa.crow.game.component.animation;

import com.rozsa.crow.game.component.Position;
import com.rozsa.crow.game.component.StaticRenderer;
import com.rozsa.crow.screen.api.Drawable;
import com.rozsa.crow.screen.attributes.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatedRenderer<TKey> extends StaticRenderer {
    public static final String DEFAULT_ANIMATED_RENDERER = "_defaultAnimatorComponent";
    private final Map<TKey, Animation> animations;

    public AnimatedRenderer(Position position, int layer, String name, boolean flipX, boolean flipY) {
        super(position, layer, name, flipX, flipY);

        animations = new HashMap<>();
    }

    @Override
    public void update() {
        super.update();

        if (isDisabled()) {
            return;
        }

        animations.values().forEach(Animation::update);
        refreshDrawings();
    }

    private void refreshDrawings() {
        drawings.clear();
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

    public void add(TKey key, Animation animation) {
        animations.put(key, animation);
    }

    public void setActive(TKey key, boolean isActive) {
        if (!animations.containsKey(key)) {
            return;
        }

        animations.get(key).setActive(isActive);
    }
}
