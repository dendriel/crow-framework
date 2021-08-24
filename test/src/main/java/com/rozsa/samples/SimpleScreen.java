package com.rozsa.samples;

import com.rozsa.crow.screen.BaseScreen;
import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Size;

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
