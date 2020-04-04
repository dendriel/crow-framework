package com.rozsa.crow.screen.ui.api;

import com.rozsa.crow.screen.ui.UIComponentType;

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
}
