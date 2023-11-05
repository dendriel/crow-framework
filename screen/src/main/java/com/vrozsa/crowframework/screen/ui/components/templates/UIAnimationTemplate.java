package com.vrozsa.crowframework.screen.ui.components.templates;

import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Rect;

import java.util.Objects;

/**
 * UI Animation template.
 */
public final class UIAnimationTemplate extends UIIconTemplate {
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

    /**
     * @param frameRect rect of the image file in which the animation resides.
     * @param timeBetweenFrames time to wait between rendering frames.
     * @param repeat repeat the animation from the start once it was finished
     * @param firstFrame starting animation frame (also starting frame when repeating the animation).
     * @param isRunning running animation state (true = animation starts running; false = animation starts idle and must
     *                  be started somewhere else).
     * @param isVisible the animation is visible? (true = visible; false = invisible)
     */
    public UIAnimationTemplate(
            Rect frameRect, long timeBetweenFrames, boolean repeat, int firstFrame, boolean isRunning, boolean isVisible) {
        super(UIComponentType.ANIMATION);
        this.frameRect = frameRect;
        this.timeBetweenFrames = timeBetweenFrames;
        this.repeat = repeat;
        this.firstFrame = firstFrame;
        this.isRunning = isRunning;
        this.isVisible = isVisible;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UIAnimationTemplate that = (UIAnimationTemplate) o;
        return timeBetweenFrames == that.timeBetweenFrames && repeat == that.repeat && firstFrame == that.firstFrame && isRunning == that.isRunning && isVisible == that.isVisible && Objects.equals(frameRect, that.frameRect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frameRect, timeBetweenFrames, repeat, firstFrame, isRunning, isVisible);
    }
}
