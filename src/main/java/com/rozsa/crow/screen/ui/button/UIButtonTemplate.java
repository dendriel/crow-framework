package com.rozsa.crow.screen.ui.button;

import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.UILabelTemplate;

public class UIButtonTemplate {
    private Rect rect;
    private String defaultImage;
    private String pressedImage;
    private String rolloverImage;
    private String disabledImage;
    private boolean isDisabled;
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

    public String getRolloverImage() {
        return rolloverImage;
    }

    public void setRolloverImage(String rolloverImage) {
        this.rolloverImage = rolloverImage;
    }

    public String getDisabledImage() {
        return disabledImage;
    }

    public void setDisabledImage(String disabledImage) {
        this.disabledImage = disabledImage;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}