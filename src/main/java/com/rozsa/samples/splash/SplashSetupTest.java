package com.rozsa.samples.splash;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ScreenHandlerConfig;
import com.rozsa.crow.screen.attributes.Size;

public class SplashSetupTest {
    private final boolean isFullScreen;

    public SplashSetupTest(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
    }

    public void run() {
        ScreenHandlerConfig config = new ScreenHandlerConfig();
        config.setTitle("Main Test Window");
        Size screenSize = new Size(800, 600);
        config.setSize(screenSize);
        config.setVisible(false);
        config.setFullscreen(isFullScreen);

        ScreenHandler<ScreenType> screen = new ScreenHandler<>(ScreenType.class, config);

        screen.setVisible(true);

        Size splashScreenSize = screen.getSize();
        SplashScreen splashScreen = new SplashScreen(splashScreenSize);
        screen.add(ScreenType.SPLASH, splashScreen);
        screen.setOnlyVisible(ScreenType.SPLASH, true);
    }
}
