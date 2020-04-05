package com.rozsa.samples;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ScreenHandlerConfig;
import com.rozsa.crow.screen.attributes.Size;

public class TestUtils {


    public static ScreenHandler<ScreenType> createDefaultScreenHandler(String title) {
        return createDefaultScreenHandler(title, false, false);
    }

    public static ScreenHandler<ScreenType> createDefaultScreenHandler(String title, boolean isFullscreen, boolean enableAnimations) {
        ScreenHandlerConfig config = new ScreenHandlerConfig();
        config.setTitle(title);
        Size screenSize = new Size(1600, 900);
//        Size screenSize = new Size(800, 600);
        config.setSize(screenSize);
        config.setVisible(false);
        config.setResizable(true);
        config.setFullscreen(isFullscreen);
        config.setAnimationsEnabled(enableAnimations);
//        config.setAnimationsFPS(60);
        config.setAnimationsFPS(8);

        ScreenHandler<ScreenType> screen = new ScreenHandler<>(config);
        screen.setVisible(true);

        return screen;
    }
}
