package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;

import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UIAnimation extends UIIcon {
    private UIAnimationTemplate data;
    private int currentFrame;
    private int totalFrames;
    private boolean isRunning;
    private long lastFrameUpdateTime;
    private Rect frameRect;

    public UIAnimation(UIAnimationTemplate data) {
        super(data);

        this.data = data;
        setup();
    }

    private void setup() {
        frameRect = data.getFrameRect();
        totalFrames = rect.getWidth() / frameRect.getWidth();
        add(this);

        isEnabled = data.isVisible();
    }

    private void reset() {
        currentFrame = data.getFirstFrame();
        isRunning = data.isRunning();
        lastFrameUpdateTime = 0;
    }

    private long timePassedSinceLastFrame() {
        return System.currentTimeMillis() - lastFrameUpdateTime;
    }

    private void updateLastFrameUpdateTime() {
        lastFrameUpdateTime = System.currentTimeMillis();
    }

    private void setupNextFrame() {
        currentFrame++;
        if (currentFrame >= totalFrames) {
            currentFrame = 0;
        }
    }

    public void start() {
        if (isRunning) {
            return;
        }
        reset();
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    protected Rect getRectToPaint() {
        Rect rectToPaint = super.getRectToPaint();
        rectToPaint.setSize(frameRect.getSize());
        return rectToPaint;
    }

    @Override
    protected BufferedImage getImageToPaint() {
        BufferedImage fullImage = image.getContent(rect.getWidth(), rect.getHeight());
        Size frameSize = frameRect.getSize();

        float frameWidth = ((float)data.getFrameRect().getSize().getWidth() / data.getRect().getSize().getWidth()) * rect.getSize().getWidth();
        int x = (int)(currentFrame * frameWidth);

        return fullImage.getSubimage(x, 0, frameSize.getWidth(), frameSize.getHeight());
    }

    @Override
    public void updateScreenSize(Size parentSize) {
        super.updateScreenSize(parentSize);

        Size refSize = data.getRect().getSize();
        Rect frameRect = data.getFrameRect();

        if (expandMode.equals(UIExpandMode.FILL)) {
            // parent from the frame is the spritesheet.
            Size newSize = Size.updateSize(frameRect.getSize(), refSize, rect.getSize());
            this.frameRect.setWidth(newSize.getWidth());
            this.frameRect.setHeight(newSize.getHeight());
        }
    }

    private static Set<UIAnimation> runningAnimations;

    private static void add(UIAnimation animation) {
        if (runningAnimations == null) {
            setupAnimator();
        }

        animation.reset();
        runningAnimations.add(animation);
    }

    private static void setupAnimator() {
        runningAnimations = ConcurrentHashMap.newKeySet();
        new Thread(UIAnimation::animatorLoop).start();
    }

    private static void animatorLoop() {
        while (true) {
            runningAnimations.forEach(UIAnimation::animate);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void animate(UIAnimation animation) {
        if (!animation.isRunning) {
            return;
        }

        if (animation.timePassedSinceLastFrame() < animation.data.getTimeBetweenFrames()) {
            return;
        }

        animation.setupNextFrame();
        animation.updateLastFrameUpdateTime();
    }
}
