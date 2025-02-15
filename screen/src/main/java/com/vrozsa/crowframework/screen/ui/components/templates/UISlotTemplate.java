package com.vrozsa.crowframework.screen.ui.components.templates;

import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Rect;

public final class UISlotTemplate extends UIBaseComponentTemplate {
    private Rect rect;
    private UIIconTemplate backgroundImage;
    private UIIconTemplate handlerImage;
    private UILabelTemplate countLabel;
    private Rect contentRect;

    public UISlotTemplate() {
        super(UIComponentType.SLOT);
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public UIIconTemplate getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(UIIconTemplate backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public UIIconTemplate getHandlerImage() {
        return handlerImage;
    }

    public void setHandlerImage(UIIconTemplate handlerImage) {
        this.handlerImage = handlerImage;
    }

    public UILabelTemplate getCountLabel() {
        return countLabel;
    }

    public void setCountLabel(UILabelTemplate countLabel) {
        this.countLabel = countLabel;
    }

    public Rect getContentRect() {
        return contentRect;
    }

    public void setContentRect(Rect contentRect) {
        this.contentRect = contentRect;
    }

    public UISlotTemplate clone() {
        UISlotTemplate clone = new UISlotTemplate();

        if (rect != null) {
            clone.setRect(rect.clone());
        }

        clone.setBackgroundImage(backgroundImage);
        clone.setHandlerImage(handlerImage);
        clone.setCountLabel(countLabel);
        clone.setContentRect(contentRect);
        return clone;
    }
}
