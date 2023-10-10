package com.vrozsa.crowframework.game.component.animation;

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

public class Animation {
    private final AnimationTemplate data;
    private long length;

    private boolean isActive;
    private boolean isTriggered;

    private List<Image> images;
    private Cooldown cooldownBeforeRepeating;

    private Rect rect;
    private int currentFrame;
    private int totalFrames;
    private long lastFrameUpdateTime;
    private Rect frameRect;

    private long lastTriggered;

    public Animation(final AnimationTemplate data) {
        this.data = data;

        cooldownBeforeRepeating = Cooldown.create(data.intervalBeforeRepeating());
        setup();
        length = data.timeBetweenFrames() * totalFrames;
    }

    private void setup() {
        rect = data.rect();
        frameRect = data.frameRect();
        totalFrames = rect.getWidth() / frameRect.getWidth();

        isActive = data.isActive();

        loadSpritesheets();
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
        }

        if (isTriggered && !active) {
            isTriggered = false;
        }

        isActive = active;
    }

    public void trigger() {
        isTriggered = true;
        isActive = false;
        lastTriggered = TimeUtils.getCurrentTime();
        reset();
    }

    public long getLastTriggered() {
        return lastTriggered;
    }

    public long getLength() {
        return length;
    }

    void reset() {
        currentFrame = data.firstFrame();
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
        int nextFrame = currentFrame + 1;
        if (nextFrame < totalFrames) {
            currentFrame = nextFrame;
            return;
        }

        if (isTriggered) {
            isTriggered = false;
            return;
        }

        if (data.repeat()) {
            repeat();
        }
    }

    private void repeat() {
        if (data.intervalBeforeRepeating() <= 0) {
            currentFrame = 0;
            return;
        }

        if (cooldownBeforeRepeating.isStopped()) {
            cooldownBeforeRepeating.start();
        }
        else if (cooldownBeforeRepeating.isFinished()) {
            cooldownBeforeRepeating.stop();
            currentFrame = 0;
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

        var frameWidth = ((float)data.frameRect().getSize().getWidth() / data.rect().getSize().getWidth()) * rect.getSize().getWidth();
        var x = (int)(currentFrame * frameWidth);

        return fullImage.getSubimage(x, 0, frameSize.getWidth(), frameSize.getHeight());
    }
}
