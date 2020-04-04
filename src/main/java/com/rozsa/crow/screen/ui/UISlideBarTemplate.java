package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;

public class UISlideBarTemplate extends UIBaseComponentTemplate {
    private Rect backgroundRect;
    private String backgroundImage;
    private Rect foregroundRect;
    private String foregroundImage;

    public UISlideBarTemplate() {
        super(UIComponentType.SLIDER);
    }

    public Rect getBackgroundRect() {
        return backgroundRect;
    }

    public void setBackgroundRect(Rect backgroundRect) {
        this.backgroundRect = backgroundRect;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getForegroundImage() {
        return foregroundImage;
    }

    public void setForegroundImage(String foregroundImage) {
        this.foregroundImage = foregroundImage;
    }

    public Rect getForegroundRect() {
        return foregroundRect;
    }

    public void setForegroundRect(Rect foregroundRect) {
        this.foregroundRect = foregroundRect;
    }

}
