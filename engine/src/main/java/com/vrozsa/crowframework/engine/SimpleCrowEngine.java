package com.vrozsa.crowframework.engine;

/**
 * Crow Engine aimed to be an out-of-box engine to easily build games using Crow Framework.
 */
class SimpleCrowEngine implements CrowEngine {

    private final SimpleInputManager inputManager;
    private final ScreenManager screenManager;

    SimpleCrowEngine(final int screenWidth, final int screenHeight) {
        inputManager = new SimpleInputManager();
        screenManager = new ScreenManager(inputManager);
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}
