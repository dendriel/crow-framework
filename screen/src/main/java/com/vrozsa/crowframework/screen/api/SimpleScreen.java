package com.vrozsa.crowframework.screen.api;

import com.vrozsa.crowframework.screen.internal.BaseScreen;
import com.vrozsa.crowframework.screen.internal.BaseView;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Size;

public class SimpleScreen extends BaseScreen<SimpleViewType, SimpleViewType> {
    public SimpleScreen(Size size) {
        this(size, Color.blue());
    }

    public SimpleScreen(Size size, Color color) {
        super(size, color);
    }

    // In real scenarios the screen will know by itself what views to create/display.
    public void addView(BaseView view) {
        addView(SimpleViewType.BASIC, view);
        addViewGroup(SimpleViewType.BASIC, SimpleViewType.BASIC);
    }

    public void displayView() {
        displayViewGroup(SimpleViewType.BASIC);
    }

    public void draw(){
        super.draw();
    }
}
