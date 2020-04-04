package com.rozsa.samples.form;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;
import com.rozsa.samples.TestUtils;

public class LoginTest {

    public void run() {
        ScreenHandler<ScreenType> screen = TestUtils.createDefaultScreenHandler("Login test");
        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize, Color.from(76, 96, 125));

        Rect rect = new Rect(0, 0, simpleScreenSize.getWidth(), simpleScreenSize.getHeight());
        LoginView view = new LoginView(rect);
        simpleScreen.addView(view);
        simpleScreen.displayView();

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);
    }
}
