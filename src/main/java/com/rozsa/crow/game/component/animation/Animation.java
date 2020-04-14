package com.rozsa.crow.game.component.animation;

import com.rozsa.crow.screen.api.Drawable;
import com.rozsa.crow.screen.api.Renderer;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Image;
import com.rozsa.crow.screen.sprite.Sprite;
import com.rozsa.crow.screen.sprite.SpriteTemplate;
import com.rozsa.crow.time.Cooldown;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    private final AnimationTemplate data;

    private Image image;
    private Cooldown cooldownBeforeRepeating;

    private Rect rect;
    private int currentFrame;
    private int totalFrames;
    private long lastFrameUpdateTime;
    private Rect frameRect;

    public Animation(AnimationTemplate data) {
        this.data = data;

        cooldownBeforeRepeating = new Cooldown(data.getIntervalBeforeRepeating());
        setup();
    }

    private void setup() {
        image = Image.load(data.getImageFile());

        rect = data.getRect();
        frameRect = data.getFrameRect();
        totalFrames = rect.getWidth() / frameRect.getWidth();
    }

    void reset() {
        currentFrame = data.getFirstFrame();
        lastFrameUpdateTime = 0;
    }

    // Probably this goes on AnimatedRenderer
    void update() {
        if (timePassedSinceLastFrame() < data.getTimeBetweenFrames()) {
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

        if (data.isRepeat()) {
            repeat();
        }
    }

    private void repeat() {
        if (data.getIntervalBeforeRepeating() <= 0) {
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
        SpriteTemplate data = new SpriteTemplate();
        data.setEnabled(true);
        data.setOrder(0);
        data.setSize(frameRect.getSize());

        Image image = new Image(getImageToPaint());
        Sprite sprite = new Sprite(data, image, true);
        sprite.setRenderer(renderer);

        List<Drawable> drawings = new ArrayList<>();
        drawings.add(sprite);
        return drawings;
    }

    private BufferedImage getImageToPaint() {
        BufferedImage fullImage = image.getContent(rect.getWidth(), rect.getHeight());
        Size frameSize = frameRect.getSize();

        float frameWidth = ((float)data.getFrameRect().getSize().getWidth() / data.getRect().getSize().getWidth()) * rect.getSize().getWidth();
        int x = (int)(currentFrame * frameWidth);

        return fullImage.getSubimage(x, 0, frameSize.getWidth(), frameSize.getHeight());
    }
}
