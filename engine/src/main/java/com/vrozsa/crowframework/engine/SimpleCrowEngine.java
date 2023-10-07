package com.vrozsa.crowframework.engine;


import com.vrozsa.crowframework.screen.internal.ScreenHandlerConfig;
import com.vrozsa.crowframework.shared.attributes.Size;

/**
 * Crow Engine aimed to be an out-of-box engine to easily build games using Crow Framework.
 */
class SimpleCrowEngine implements CrowEngine {
    private final CrowEngineConfig config;
    private final SimpleInputManager inputManager;
    private final CrowScreenManager screenManager;

    SimpleCrowEngine(final CrowEngineConfig config) {
        this.config = config;
        inputManager = new SimpleInputManager();

        screenManager = setupScreenManager();
    }

    private CrowScreenManager setupScreenManager() {
        var screenHandlerConfig = ScreenHandlerConfig.builder()
                .size(Size.of(config.screenWidth(), config.screenHeight()))
                .build();
        return new CrowScreenManager(config.color(), screenHandlerConfig, inputManager.getInputHandler());
    }

    @Override
    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
    public CrowScreenManager getScreenManager() {
        return screenManager;
    }
}
