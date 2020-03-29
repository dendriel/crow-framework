package com.rozsa.samples.components.button;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ScreenHandlerConfig;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;

public class ButtonTest {
    public ButtonTest() {
    }

    public void run() {
        ScreenHandlerConfig config = new ScreenHandlerConfig();
        config.setTitle("Button Test Window");
        Size screenSize = new Size(800, 600);
        config.setSize(screenSize);
        config.setVisible(false);
        config.setFullscreen(false);

        ScreenHandler<ScreenType> screen = new ScreenHandler<>(ScreenType.class, config);

        screen.setVisible(true);

        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize);

        Rect rect = new Rect(0, 0, screenSize.getWidth(), screenSize.getHeight());
        ButtonsView view = new ButtonsView(rect);
        simpleScreen.addView(view);
        simpleScreen.displayView();

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);
    }
}
