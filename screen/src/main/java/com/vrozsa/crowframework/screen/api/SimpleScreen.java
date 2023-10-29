package com.vrozsa.crowframework.screen.api;

import com.vrozsa.crowframework.screen.internal.AbstractScreen;
import com.vrozsa.crowframework.shared.api.screen.View;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Size;

public class SimpleScreen extends AbstractScreen {
    public SimpleScreen(final String name, final Size size) {
        this(name, size, Color.blue());
    }

    public SimpleScreen(final String name, final Size size, final Color color) {
        super(name, size, color);
    }

    // In real scenarios the screen will know by itself what views to create/display.
    public void addView(final View view) {
        super.addView(view.getName(), view);
    }

//    public void addView(final BaseView view, final String viewGroup) {
//        super.addView(view.name(), view);
//        addViewGroup(viewGroup, view.name());
//    }

//    public void displayView() {
//        displayViewGroup(SimpleViewType.BASIC.name());
//    }

//    public void displayView(final String name) {
//        displayViewGroup(name);
//    }

    public void draw(){
        super.draw();
    }
}
