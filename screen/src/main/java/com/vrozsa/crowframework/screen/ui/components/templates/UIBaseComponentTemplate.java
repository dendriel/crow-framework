package com.vrozsa.crowframework.screen.ui.components.templates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

import static java.util.Objects.isNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UIBaseComponentTemplate {
    protected static final UIExpandMode DEFAULT_EXPAND_MODE = UIExpandMode.FILL;
    protected static final boolean DEFAULT_IS_ENABLED = true;

    protected final UIComponentType type;
    protected Rect rect;
    protected String tag;
    protected UIExpandMode expandMode;
    // auto-set property
    protected Size referenceSize;
    protected boolean isEnabled;

    public UIBaseComponentTemplate(final UIComponentType type) {
        this.type = type;
        expandMode = DEFAULT_EXPAND_MODE;
        isEnabled = DEFAULT_IS_ENABLED;
    }

    public UIBaseComponentTemplate(
            UIComponentType type, Rect rect, String tag, UIExpandMode expandMode, Size referenceSize, boolean isEnabled) {
        this.type = type;
        this.rect = rect;
        this.tag = tag;
        this.expandMode = expandMode;
        this.referenceSize = referenceSize;
        this.isEnabled = isEnabled;
    }

    protected UIBaseComponentTemplate(final UIBaseComponentTemplate from) {
        this.type = from.type;
        this.rect = from.getRect();
        this.tag = from.tag;
        this.expandMode = from.expandMode;
        this.referenceSize = from.getReferenceSize().clone();
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
        if (isNull(referenceSize)) {
            return Size.zeroed();
        }
        return referenceSize.clone();
    }

    public void setReferenceSize(Size referenceSize) {
        this.referenceSize = referenceSize;
    }

    public Rect getRect() {
        return isNull(rect) ? Rect.zeroed() : rect.clone();
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
