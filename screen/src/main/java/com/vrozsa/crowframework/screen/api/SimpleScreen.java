package com.vrozsa.crowframework.screen.api;

import com.vrozsa.crowframework.screen.internal.BaseScreen;
import com.vrozsa.crowframework.screen.internal.BaseView;
import com.vrozsa.crowframework.screen.ui.api.UIComponent;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

import java.util.Objects;

public class SimpleScreen extends BaseScreen<SimpleViewType, SimpleViewType> {
    public SimpleScreen(Size size) {
        this(size, Color.blue());
    }

    public SimpleScreen(Size size, Color color) {
        super(size, color);
    }

    // In real scenarios the screen will know by itself what views to create/display.
    public void addView(BaseView view) {
        super.addView(SimpleViewType.BASIC, view);
        addViewGroup(SimpleViewType.BASIC, SimpleViewType.BASIC);
    }

    public void addView(SimpleViewType type, BaseView view) {
        super.addView(type, view);
        addViewGroup(type, type);
    }

    public void displayView() {
        displayViewGroup(SimpleViewType.BASIC);
    }

    public void displayView(SimpleViewType type) {
        displayViewGroup(type);
    }

    /**
     * Adds a new UI component to the screen
     * @param component component to be added
     */
    public <T> void addComponent(final UIComponent<T> component) {
        getView(SimpleViewType.STATIC).addComponent(component);
    }

    public void draw(){
        super.draw();
    }
}
