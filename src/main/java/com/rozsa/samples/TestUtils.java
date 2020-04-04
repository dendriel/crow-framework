package com.rozsa.samples;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ScreenHandlerConfig;
import com.rozsa.crow.screen.attributes.Size;

public class TestUtils {


    public static ScreenHandler<ScreenType> createDefaultScreenHandler(String title) {
        return createDefaultScreenHandler(title, false);
    }

    public static ScreenHandler<ScreenType> createDefaultScreenHandler(String title, boolean isFullscreen) {
        ScreenHandlerConfig config = new ScreenHandlerConfig();
        config.setTitle(title);
        Size screenSize = new Size(1600, 900);
        config.setSize(screenSize);
        config.setVisible(false);
        config.setResizable(true);
        config.setFullscreen(isFullscreen);

        ScreenHandler<ScreenType> screen = new ScreenHandler<>(ScreenType.class, config);
        screen.setVisible(true);

        return screen;
    }
}
