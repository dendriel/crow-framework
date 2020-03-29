package com.rozsa.samples;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ScreenHandlerConfig;
import com.rozsa.crow.screen.attributes.Size;

public class TestUtils {


    public static ScreenHandler<ScreenType> createDefaultScreenHandler(String title) {
        ScreenHandlerConfig config = new ScreenHandlerConfig();
        config.setTitle(title);
        Size screenSize = new Size(800, 600);
        config.setSize(screenSize);
        config.setVisible(false);
        config.setFullscreen(false);

        ScreenHandler<ScreenType> screen = new ScreenHandler<>(ScreenType.class, config);
        screen.setVisible(true);

        return screen;
    }
}
