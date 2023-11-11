package com.vrozsa.crowframework.screen.ui.components.templates;

public class CustomScrollBarUITemplate {
    private String thumb;
    private String track;
    private UIButtonTemplate increaseButton;
    private UIButtonTemplate decreaseButton;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public UIButtonTemplate getIncreaseButton() {
        return increaseButton;
    }

    public void setIncreaseButton(UIButtonTemplate increaseButton) {
        this.increaseButton = increaseButton;
    }

    public UIButtonTemplate getDecreaseButton() {
        return decreaseButton;
    }

    public void setDecreaseButton(UIButtonTemplate decreaseButton) {
        this.decreaseButton = decreaseButton;
    }
}
