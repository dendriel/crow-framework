package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.ui.api.UIComponent;
import com.rozsa.crow.screen.ui.api.UIComponentObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class UIBaseComponent<T> implements UIComponent<T> {
    private final UIBaseComponentTemplate data;
    protected final List<UIComponentObserver> observers;
    protected final UIComponentType type;
    protected final UIExpandMode expandMode;
    protected final String tag;
    protected Rect rect;
    protected Offset parentOffset;
    protected boolean isEnabled = true;

    public UIBaseComponent(UIBaseComponentTemplate data) {
        this.data = data;
        type = data.getType();
        tag = data.getTag();
        expandMode = data.getExpandMode();
        parentOffset = Offset.origin();
        observers = new ArrayList<>();
    }

    public void addUIComponentChangedListener(UIComponentObserver observer) {
        observers.add(observer);
    }

    public void removeUIComponentChangedListener(UIComponentObserver observer) {
        observers.remove(observer);
    }

    protected void onComponentChanged() {
        observers.forEach(UIComponentObserver::componentChanged);
    }

    public void wrapUp(Container container) {
    }

    public void destroy(Container container) {
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public UIComponentType getType() {
        return type;
    }

    @Override
    public String getTag() {
        return tag;
    }

    public void updateScreenSize(Size parentSize) {
        Size refSize = data.getReferenceSize();
        Rect rect = data.getRect();
        parentOffset = Offset.updateOffset(rect.getOffset(), refSize, parentSize);

        if (expandMode.equals(UIExpandMode.FILL)) {
            Size newSize = Size.updateSize(rect.getSize(), refSize, parentSize);
            this.rect.setWidth(newSize.getWidth());
            this.rect.setHeight(newSize.getHeight());
        }
    }
}
