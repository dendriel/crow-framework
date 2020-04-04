package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.ui.api.UIComponent;
import com.rozsa.crow.screen.ui.api.UIComponentObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class UIBaseComponent<T> implements UIComponent<T> {
    protected final List<UIComponentObserver> observers;
    protected final UIComponentType type;
    protected final UIExpandMode expandMode;
    protected final String tag;
    protected boolean isEnabled = true;

    public UIBaseComponent(UIBaseComponentTemplate data) {
        type = data.getType();
        tag = data.getTag();
        expandMode = data.getExpandMode();
        observers = new ArrayList<>();
    }

    public void addUIComponentChangedListener(UIComponentObserver observer) {
        observers.add(observer);
    }

    public void removeUIComponentChangedListener(UIComponentObserver observer) {
        observers.remove(observer);
    }

    protected void onComponentChanged() {
        observers.forEach(o -> o.componentChanged());
    }

    public void wrapUp(Container container) {
    }

    public void destroy(Container container) {
    }

    @Override
    public UIComponentType getType() {
        return type;
    }

    @Override
    public String getTag() {
        return tag;
    }
}
