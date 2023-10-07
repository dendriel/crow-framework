package com.vrozsa.crowframework.engine;


import com.vrozsa.crowframework.screen.api.ScreenType;
import com.vrozsa.crowframework.screen.api.SimpleScreen;
import com.vrozsa.crowframework.screen.factory.SimpleScreenFactory;
import com.vrozsa.crowframework.screen.internal.ScreenHandlerConfig;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Size;

/**
 * Crow Engine aimed to be an out-of-box engine to easily build games using Crow Framework.
 */
class SimpleCrowEngine implements CrowEngine {
    private final CrowEngineConfig config;
    private final SimpleInputManager inputManager;
    private final ScreenManager screenManager;

    SimpleCrowEngine(final CrowEngineConfig config) {
        this.config = config;
        inputManager = new SimpleInputManager();

        screenManager = setupScreenManager();
    }

    private ScreenManager setupScreenManager() {
        var screenSize = Size.of(config.screenWidth(), config.screenHeight());
        ScreenHandlerConfig screenHandlerConfig = ScreenHandlerConfig.builder()
                .size(screenSize.clone())
                .build();

        var simpleScreen = new SimpleScreen(screenSize.clone(), config.color());
        var screenManager = new ScreenManager(screenHandlerConfig, inputManager.getInputHandler());

        screenManager.addScreen(simpleScreen.name(), simpleScreen);
        screenManager.setOnlyScreenVisible(simpleScreen.name(), true);

        return screenManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}
