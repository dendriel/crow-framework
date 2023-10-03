package com.vrozsa.crowframework.screen.ui.api;

import com.vrozsa.crowframework.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Size;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;


public interface UIComponent<T> {
    void wrapUp(Container container);

    void destroy(Container container);

    void paint(Graphics g, ImageObserver observer);

    void addUIComponentChangedListener(UIComponentObserver observer);

    void removeUIComponentChangedListener(UIComponentObserver observer);

    void updateComponentTemplate(T data) throws IOException;

    void show();

    void hide();

    UIComponentType getType();

    String getTag();

    void updateScreenSize(Size parentSize);
}
