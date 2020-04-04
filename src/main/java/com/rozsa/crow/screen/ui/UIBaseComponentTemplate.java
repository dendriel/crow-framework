package com.rozsa.crow.screen.ui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UIBaseComponentTemplate {
    protected final UIComponentType type;
    protected String tag;

    public UIBaseComponentTemplate(UIComponentType type) {
        this.type = type;
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
}
