package com.rozsa.samples.components.input;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ScreenHandlerConfig;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;
import com.rozsa.samples.TestUtils;
import com.rozsa.samples.components.button.ButtonsView;

public class InputTest {
    public InputTest() {
    }

    public void run() {
        ScreenHandler<ScreenType> screen = TestUtils.createDefaultScreenHandler("Input test");

        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize);

        Rect rect = new Rect(0, 0, simpleScreenSize.getWidth(), simpleScreenSize.getHeight());
        InputView view = new InputView(rect);
        simpleScreen.addView(view);
        simpleScreen.displayView();

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);
    }
}
