package com.vrozsa.crowframework.screen.ui.components.templates;

import com.vrozsa.crowframework.screen.ui.components.UIFillBar;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Rect;

public final class UIFillBarTemplate extends UIBaseComponentTemplate {
    private UIFillBar.Type fillBarType;
    private boolean staticImageAbove;
    private Rect staticRect;
    private String staticImage;
    private Rect dynamicRect;
    private String dynamicImage;
    private float fill;
    private boolean reverseFill;

    public UIFillBarTemplate() {
        super(UIComponentType.FILL_BAR);
        fill = 1;
    }

    public UIFillBar.Type getFillBarType() {
        return fillBarType;
    }

    public void setFillBarType(UIFillBar.Type fillBarType) {
        this.fillBarType = fillBarType;
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
