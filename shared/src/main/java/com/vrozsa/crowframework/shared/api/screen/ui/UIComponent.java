package com.vrozsa.crowframework.shared.api.screen.ui;

import com.vrozsa.crowframework.shared.attributes.Size;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;


/**
 * UI Components are components that can be added and displayed over screens and views.
 * @param <T> type of template data for the component.
 */
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
