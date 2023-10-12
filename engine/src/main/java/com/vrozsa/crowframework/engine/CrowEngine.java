package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.attributes.Color;

/**
 * Crow Engine public API.
 */
public interface CrowEngine {
    /**
     * Creates a new CrowEngine with default configurations.
     * @return the created CrowEngine.
     */
    static CrowEngine create() {
        var config = CrowEngineConfig.builder().build();
        return create(config);
    }

    /**
     * Creates a new CrowEngine with custom configurations.
     * @param screenWidth target screen width.
     * @param screenHeight target screen height.
     * @param color target screen background color (displayed when there is no components over in the screen).
     * @return the created CrowEngine.
     */
    static CrowEngine create(final int screenWidth, final int screenHeight, final Color color) {
        var config = CrowEngineConfig.builder()
                .screenWidth(screenWidth)
                .screenHeight(screenHeight)
                .color(color)
                .build();
        return create(config);
    }

    /**
     * Creates a new CrowEngine with custom configurations.
     * @param config CrowEngine configuration.
     * @return the new CrowEngine object.
     */
    static CrowEngine create(final CrowEngineConfig config) {
        return new Engine(config);
    }

    /**
     * Adds a new game object into the game.
     * @param go the game object to be added.
     */
    void addGameObject(final GameObject go);

    /**
     * Gets the InputManager instance.
     * @return the input manager.
     */
    InputManager getInputManager();

    /**
     * Gets the ScreenManager instance.
     * @return the screen manager.
     */
    ScreenManager getScreenManager();

    /**
     * Gets the GameManager instance.
     * @return the game manager.
     */
    GameManager getGameManager();
}
