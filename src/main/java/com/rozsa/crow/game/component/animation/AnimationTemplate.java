package com.rozsa.crow.game.component.animation;

import com.rozsa.crow.screen.attributes.Rect;

public class AnimationTemplate {
    private Rect rect;
    private String imageFile;
    private Rect frameRect;
    private long timeBetweenFrames;
    private int firstFrame;
    private boolean repeat;
    private long intervalBeforeRepeating;

    public AnimationTemplate clone() {
        AnimationTemplate clone = new AnimationTemplate();
        clone.setRect(rect.clone());
        clone.setImageFile(imageFile);
        clone.setFrameRect(frameRect.clone());
        clone.setTimeBetweenFrames(timeBetweenFrames);
        clone.setFirstFrame(firstFrame);
        clone.setRepeat(repeat);
        clone.setIntervalBeforeRepeating(intervalBeforeRepeating);

        return clone;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Rect getFrameRect() {
        return frameRect;
    }

    public void setFrameRect(Rect frameRect) {
        this.frameRect = frameRect;
    }

    public long getTimeBetweenFrames() {
        return timeBetweenFrames;
    }

    public void setTimeBetweenFrames(long timeBetweenFrames) {
        this.timeBetweenFrames = timeBetweenFrames;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public int getFirstFrame() {
        return firstFrame;
    }

    public void setFirstFrame(int firstFrame) {
        this.firstFrame = firstFrame;
    }

    public long getIntervalBeforeRepeating() {
        return intervalBeforeRepeating;
    }

    public void setIntervalBeforeRepeating(long intervalBeforeRepeating) {
        this.intervalBeforeRepeating = intervalBeforeRepeating;
    }
}
