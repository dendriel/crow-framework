package com.rozsa.screen.ui;

import com.rozsa.shared.attributes.Rect;

public class UIAnimationTemplate extends UIIconTemplate {
    private Rect frameRect;
    private long timeBetweenFrames;
    private boolean repeat;
    private int firstFrame;
    private boolean isRunning;
    private boolean isVisible;

    public UIAnimationTemplate() {
        super(UIComponentType.ANIMATION);
        repeat = true;
        isRunning = true;
        isVisible = true;
    }

    public int getFirstFrame() {
        return firstFrame;
    }

    public void setFirstFrame(int firstFrame) {
        this.firstFrame = firstFrame;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public long getTimeBetweenFrames() {
        return timeBetweenFrames;
    }

    public void setTimeBetweenFrames(long timeBetweenFrames) {
        this.timeBetweenFrames = timeBetweenFrames;
    }

    public Rect getFrameRect() {
        return frameRect.clone();
    }

    public void setFrameRect(Rect frameRect) {
        this.frameRect = frameRect;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
