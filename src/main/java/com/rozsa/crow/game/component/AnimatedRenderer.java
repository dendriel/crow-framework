package com.rozsa.crow.game.component;

import com.rozsa.crow.game.attributes.Animation;

import java.util.HashMap;
import java.util.Map;

public class AnimatedRenderer<TKey> extends BaseComponent {
    private static final String DEFAULT_ANIMATOR = "_defaultAnimatorComponent";
    private final Map<TKey, Animation> animations;
    private final Animation nullAnimation;

    private StaticRenderer renderer;
    private Animation currAnimating;

    public AnimatedRenderer(boolean isEnabled) {
        this(isEnabled, DEFAULT_ANIMATOR);
    }

    public AnimatedRenderer(boolean isEnabled, String name) {
        super(isEnabled, name);
        animations = new HashMap<>();

        nullAnimation = new Animation();
    }

    @Override
    public void wrapUp() {
        super.wrapUp();

        renderer = getComponent(StaticRenderer.class);
        assert renderer != null : "Animator requires a Renderer!";
    }

    @Override
    public void update() {
        super.update();

        if (isDisabled()) {
            return;
        }


    }

    public void add(TKey key, Animation animation) {
        animations.put(key, animation);
    }

    public void run(TKey key) {
        currAnimating = animations.getOrDefault(key, nullAnimation);
        currAnimating.reset();
    }
}
