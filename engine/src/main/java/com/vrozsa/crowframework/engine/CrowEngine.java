package com.vrozsa.crowframework.engine;

/**
 * Crow Engine public API.
 */
public interface CrowEngine {
    int DEFAULT_SCREEN_WIDTH = 800;
    int DEFAULT_SCREEN_HEIGHT = 600;

    static CrowEngine create() {
        return create(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
    }

    static CrowEngine create(final int screenWidth, final int screenHeight) {
        return new SimpleCrowEngine(screenWidth,screenHeight);
    }

    InputManager getInputManager();
}
