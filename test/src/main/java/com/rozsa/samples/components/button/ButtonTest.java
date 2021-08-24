package com.rozsa.samples.components.button;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ScreenHandlerConfig;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;
import com.rozsa.samples.TestUtils;

public class ButtonTest {
    public ButtonTest() {
    }

    public void run() {
        ScreenHandler<ScreenType> screen = TestUtils.createDefaultScreenHandler("Button test");

        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize);

        Rect rect = new Rect(0, 0, simpleScreenSize.getWidth(), simpleScreenSize.getHeight());
        ButtonsView view = new ButtonsView(rect);
        simpleScreen.addView(view);
        simpleScreen.displayView();

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);
    }
}
