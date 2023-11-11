package com.vrozsa.crowframework.screen.ui.components;

import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.screen.ui.components.templates.UIBaseComponentTemplate;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponent;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentObserver;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUIComponent<T extends UIBaseComponentTemplate> implements UIComponent<T> {
    private final UIBaseComponentTemplate data;
    protected final List<UIComponentObserver> observers;
    protected final UIComponentType type;
    protected final UIExpandMode expandMode;
    protected final String tag;
    protected Rect rect;
    protected Offset parentOffset;
    protected boolean isEnabled;

    protected AbstractUIComponent(UIBaseComponentTemplate data) {
        this.data = data;
        type = data.getType();
        tag = data.getTag();
        expandMode = data.getExpandMode();
        parentOffset = Offset.origin();
        observers = new ArrayList<>();
        isEnabled = data.isEnabled();
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

    // TODO: remove it so subclass are forced to implement.
    public void wrapUp(Container container) {
    }

    // TODO: remove it so subclass are forced to implement.
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

    @Override
    public String getName() {
        return data.getName();
    }

    public void updateScreenSize(Size parentSize) {
        var refSize = data.getReferenceSize();
        var rect = data.getRect();
        parentOffset = Offset.updateOffset(rect.getOffset(), refSize, parentSize);

        if (UIExpandMode.FILL == expandMode) {
            var newSize = Size.updateSize(rect.getSize(), refSize, parentSize);
            this.rect.setWidth(newSize.getWidth());
            this.rect.setHeight(newSize.getHeight());
        }
    }
}
