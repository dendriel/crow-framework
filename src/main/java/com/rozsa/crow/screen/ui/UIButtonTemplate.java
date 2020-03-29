package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;

public class UIButtonTemplate {
    private Rect rect;
    private String defaultImage;
    private String pressedImage;
    private UILabelTemplate label;

    public UILabelTemplate getLabel() {
        return label;
    }

    public void setLabel(UILabelTemplate label) {
        this.label = label;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getPressedImage() {
        return pressedImage;
    }

    public void setPressedImage(String pressedImage) {
        this.pressedImage = pressedImage;
    }
}
