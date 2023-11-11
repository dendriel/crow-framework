package com.vrozsa.crowframework.game.component.animation;

import com.vrozsa.crowframework.shared.api.game.AnimationTriggerEndedObserver;
import com.vrozsa.crowframework.shared.api.screen.Drawable;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.api.screen.Image;
import com.vrozsa.crowframework.shared.api.screen.Sprite;
import com.vrozsa.crowframework.shared.image.ImageLoader;
import com.vrozsa.crowframework.shared.templates.SpriteTemplate;
import com.vrozsa.crowframework.shared.time.Cooldown;
import com.vrozsa.crowframework.shared.time.TimeUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Animation instance.
 * <p>
 *     Controls de displaying of an animation.
 * </p>
 */
final class Animation {
    private final AnimationTemplate data;
    private long length;

    private boolean isActive;
    private boolean isTriggered;

    private List<Image> images;
    private Cooldown cooldownBeforeRepeating;

    private Rect rect;
    private int currentFrameIdx;
    private int totalFrames;
    private long lastFrameUpdateTime;
    private Rect frameRect;
    private long lastTriggered;
    private List<AnimationTriggerEndedObserver> triggerEndedObservers;

    private Animation(final AnimationTemplate data) {
        this.data = data;

        cooldownBeforeRepeating = Cooldown.create(data.intervalBeforeRepeating());
        setup();
        length = data.timeBetweenFrames() * totalFrames;

        triggerEndedObservers = new ArrayList<>();
    }

    static Animation of(final AnimationTemplate template) {
        return new Animation(template);
    }

    private void setup() {
        rect = data.rect();
        frameRect = data.frameRect();
        totalFrames = rect.getWidth() / frameRect.getWidth();

        isActive = data.isActive();

        loadSpritesheets();
    }

    public void addTriggerEndedObserver(final AnimationTriggerEndedObserver observer) {
        triggerEndedObservers.add(observer);
    }

    public void removeTriggerEndedObserver(final AnimationTriggerEndedObserver observer) {
        triggerEndedObservers.remove(observer);
    }

    private void onTriggerEnded() {
        triggerEndedObservers.forEach(AnimationTriggerEndedObserver::triggerEnded);
    }

    private void loadSpritesheets() {
        images = new ArrayList<>();
        for (var spritesheet : data.spritesheets()) {
            var image = ImageLoader.load(spritesheet);
            images.add(image);
        }
    }

    public boolean isActive() {
        return isActive || isTriggered;
    }

    public void setActive(boolean active) {
        if (active && !isActive) {
            reset();
            updateLastFrameUpdateTime();
        }

        if (isTriggered && !active) {
            isTriggered = false;
            onTriggerEnded();
        }

        isActive = active;
    }

    public void trigger() {
        isTriggered = true;
        isActive = false;
        lastTriggered = TimeUtils.getCurrentTime();
        reset();
        updateLastFrameUpdateTime();
    }

    public long getLastTriggered() {
        return lastTriggered;
    }

    public long getLength() {
        return length;
    }

    void reset() {
        currentFrameIdx = data.firstFrame();
        lastFrameUpdateTime = 0;
    }

    void update() {
        if (!isActive && !isTriggered) {
            return;
        }

        if (timePassedSinceLastFrame() < data.timeBetweenFrames()) {
            return;
        }

        setupNextFrame();
        updateLastFrameUpdateTime();
    }

    private long timePassedSinceLastFrame() {
        return System.currentTimeMillis() - lastFrameUpdateTime;
    }

    private void updateLastFrameUpdateTime() {
        lastFrameUpdateTime = System.currentTimeMillis();
    }

    private void setupNextFrame() {
        int nextFrameIdx = currentFrameIdx + 1;
        if (nextFrameIdx < totalFrames) {
            currentFrameIdx = nextFrameIdx;
            return;
        }

        if (isTriggered) {
            isTriggered = false;
            onTriggerEnded();
            return;
        }

        if (data.repeat()) {
            repeat();
        }
    }

    public boolean isLastFrame() {
        return (currentFrameIdx + 1) >= totalFrames;
    }

    private void repeat() {
        if (data.intervalBeforeRepeating() <= 0) {
            currentFrameIdx = 0;
            return;
        }

        if (cooldownBeforeRepeating.isStopped()) {
            cooldownBeforeRepeating.start();
        }
        else if (cooldownBeforeRepeating.isFinished()) {
            cooldownBeforeRepeating.stop();
            currentFrameIdx = 0;
        }
    }

    Size getFrameSize() {
        return frameRect.getSize();
    }

    List<Drawable> getDrawings(Renderer renderer) {
        var spriteData = SpriteTemplate.builder()
        .enabled(true)
        .order(0)
        .size(frameRect.getSize())
        .offset(rect.getOffset())
                .build();

        var drawings = new ArrayList<Drawable>();
        for (Image image : images) {
            var drawing = ImageLoader.of(getImageToPaint(image));
            var sprite = Sprite.recreate(spriteData.copy(), drawing, true);
            sprite.setRenderer(renderer);
            drawings.add(sprite);
        }

        return drawings;
    }

    private BufferedImage getImageToPaint(Image image) {
        BufferedImage fullImage = image.getContent(rect.getWidth(), rect.getHeight());
        var frameSize = frameRect.getSize();

        var frameWidth = ((double)data.frameRect().getSize().getWidth() / data.rect().getSize().getWidth()) * rect.getSize().getWidth();
        var x = (int)(currentFrameIdx * frameWidth);

        return fullImage.getSubimage(x, 0, frameSize.getWidth(), frameSize.getHeight());
    }
}
