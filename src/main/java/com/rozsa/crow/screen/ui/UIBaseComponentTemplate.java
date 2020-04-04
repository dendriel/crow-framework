package com.rozsa.crow.screen.ui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rozsa.crow.screen.attributes.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UIBaseComponentTemplate {
    protected final UIComponentType type;
    protected String tag;
    protected UIExpandMode expandMode;
    // auto-set property
    protected Size referenceSize;

    public UIBaseComponentTemplate(UIComponentType type) {
        this.type = type;
        expandMode = UIExpandMode.NONE;
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
        return referenceSize;
    }

    public void setReferenceSize(Size referenceSize) {
        this.referenceSize = referenceSize;
    }
}
