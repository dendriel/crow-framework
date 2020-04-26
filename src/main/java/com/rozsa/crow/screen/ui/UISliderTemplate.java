package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Rect;

public class UISliderTemplate extends UIBaseComponentTemplate {
    private UISlider.Type sliderType;
    private boolean staticImageAbove;
    private Rect staticRect;
    private String staticImage;
    private Rect dynamicRect;
    private String dynamicImage;
    private float fill;
    private boolean reverseFill;

    public UISliderTemplate() {
        super(UIComponentType.SLIDER);
        fill = 1;
    }

    public UISlider.Type getSliderType() {
        return sliderType;
    }

    public void setSliderType(UISlider.Type sliderType) {
        this.sliderType = sliderType;
    }

    public boolean isStaticImageAbove() {
        return staticImageAbove;
    }

    public void setStaticImageAbove(boolean staticImageAbove) {
        this.staticImageAbove = staticImageAbove;
    }

    public Rect getStaticRect() {
        return staticRect.clone();
    }

    public void setStaticRect(Rect staticRect) {
        this.staticRect = staticRect;
    }

    public String getStaticImage() {
        return staticImage;
    }

    public void setStaticImage(String staticImage) {
        this.staticImage = staticImage;
    }

    public String getDynamicImage() {
        return dynamicImage;
    }

    public void setDynamicImage(String dynamicImage) {
        this.dynamicImage = dynamicImage;
    }

    public Rect getDynamicRect() {
        return dynamicRect.clone();
    }

    public void setDynamicRect(Rect dynamicRect) {
        this.dynamicRect = dynamicRect;
    }

    public float getFill() {
        return fill;
    }

    public void setFill(float fill) {
        this.fill = fill;
    }

    public boolean isReverseFill() {
        return reverseFill;
    }

    public void setReverseFill(boolean reverseFill) {
        this.reverseFill = reverseFill;
    }
}
