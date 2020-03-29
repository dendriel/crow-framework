package com.rozsa.samples;

import com.rozsa.crow.screen.BaseScreen;
import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.attributes.Size;

public class SimpleScreen extends BaseScreen<SimpleViewType, SimpleViewType> {
    public SimpleScreen(Size size) {
        super(size);
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
