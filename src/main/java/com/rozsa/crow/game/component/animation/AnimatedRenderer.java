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

    private TKey currAnimating;

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

        Animation animation = animations.get(currAnimating);
        if (animation == null) {
            return;
        }

        animation.update();
    }

    @Override
    public Size getSize() {
        Animation animation = animations.get(currAnimating);
        if (animation == null) {
            return Size.zeroed();
        }

        // TODO.
        return animation.getFrameSize();
    }

    @Override
    public List<Drawable> getDrawings(boolean filterInactive) {
        Animation animation = animations.get(currAnimating);
        if (animation == null) {
            return new ArrayList<>();
        }

        return animation.getDrawings(this);
    }

    public void add(TKey key, Animation animation) {
        animations.put(key, animation);
    }

    public void run(TKey key) {
        if (!animations.containsKey(key)) {
            return;
        }
        currAnimating = key;
        animations.get(key).reset();
    }
}
