package com.vrozsa.crowframework.sample.screen.splash;

import com.vrozsa.crowframework.sample.screen.ScreenType;
import com.vrozsa.crowframework.sample.screen.SimpleScreen;
import com.vrozsa.crowframework.screen.ScreenHandler;
import com.vrozsa.crowframework.screen.ScreenHandlerConfig;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

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
        config.setTerminateOnWindowCloseClick(true);

        ScreenHandler<ScreenType> screen = new ScreenHandler<>(config);

        screen.setVisible(true);

        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize);

        Rect rect = new Rect(0, 0, screenSize.getWidth(), screenSize.getHeight());
        StaticView staticView = new StaticView(rect);
        simpleScreen.addView(staticView);
        simpleScreen.displayView();

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);
    }
}
