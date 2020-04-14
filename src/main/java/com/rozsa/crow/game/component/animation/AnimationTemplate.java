package com.rozsa.crow.game.component.animation;

import com.rozsa.crow.screen.attributes.Rect;

import java.util.ArrayList;
import java.util.List;

public class AnimationTemplate {
    private Rect rect;
    private List<String> spritesheets;
    private Rect frameRect;
    private long timeBetweenFrames;
    private int firstFrame;
    private boolean repeat;
    private long intervalBeforeRepeating;

    public AnimationTemplate clone() {
        AnimationTemplate clone = new AnimationTemplate();
        clone.setRect(rect.clone());
        clone.setSpritesheets(new ArrayList<>(spritesheets));
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

    public List<String> getSpritesheets() {
        return spritesheets;
    }

    public void setSpritesheets(List<String> spritesheets) {
        this.spritesheets = spritesheets;
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
