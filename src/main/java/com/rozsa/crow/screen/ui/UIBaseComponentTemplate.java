package com.rozsa.crow.screen.ui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UIBaseComponentTemplate {
    protected final UIComponentType type;
    protected Rect rect;
    protected String tag;
    protected UIExpandMode expandMode;
    // auto-set property
    protected Size referenceSize;
    protected boolean isEnabled;

    public UIBaseComponentTemplate(UIComponentType type) {
        this.type = type;
        expandMode = UIExpandMode.FILL;
        isEnabled = true;
    }

    protected UIBaseComponentTemplate(UIBaseComponentTemplate from) {
        this.type = from.type;
        this.rect = from.rect.clone();
        this.tag = from.tag;
        this.expandMode = from.expandMode;
        this.referenceSize = from.referenceSize.clone();
        this.isEnabled = from.isEnabled;
    }

    public UIBaseComponentTemplate clone() {
        return new UIBaseComponentTemplate(this);
    }

    public UIComponentType getType() {
        return type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public UIExpandMode getExpandMode() {
        return expandMode;
    }

    public void setExpandMode(UIExpandMode expandMode) {
        this.expandMode = expandMode;
    }

    public Size getReferenceSize() {
        return referenceSize.clone();
    }

    public void setReferenceSize(Size referenceSize) {
        this.referenceSize = referenceSize;
    }

    public Rect getRect() {
        return rect.clone();
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
