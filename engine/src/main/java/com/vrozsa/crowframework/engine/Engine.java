package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.game.GameLoop;
import com.vrozsa.crowframework.screen.WindowHandlerConfig;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.input.KeysListener;
import com.vrozsa.crowframework.shared.api.input.PointerListener;

/**
 * Crow Engine aimed to be an out-of-box engine to easily build games using Crow Framework.
 */
final class Engine implements CrowEngine {
    private final CrowInputManager inputManager;
    private final CrowScreenManager screenManager;
    private final CrowGameManager gameManager;
    private final CrowAudioManager audioManager;
    private final GameLoop gameLoop;

    Engine(final CrowEngineConfig config) {
        screenManager = new CrowScreenManager(config.windowMode(), config.showGizmos(), mapScreenHandlerConfig(config));
        inputManager = new CrowInputManager(screenManager);
        gameManager = new CrowGameManager(screenManager);
        audioManager = new CrowAudioManager(config.assetsPath());

        var inputHandler = (KeysListener)inputManager.getInputHandler();
        var pointerHandler = (PointerListener)inputManager.getPointerHandler();
        screenManager.setupInputListeners(inputHandler, pointerHandler);
        screenManager.setup(config.color());

        gameLoop = GameLoop.get();
        gameLoop.setScreenUpdateListener(screenManager::update);
        gameLoop.setCollisionUpdateListener(gameManager::handleCollision);
        gameLoop.addEarlyUpdateListener(gameManager::earlyUpdate);
        gameLoop.addUpdateListener(gameManager::update);
        gameLoop.addLateUpdateListener(gameManager::lateUpdate);

        gameLoop.start();
    }

    private static WindowHandlerConfig mapScreenHandlerConfig(CrowEngineConfig config) {
        return WindowHandlerConfig.builder()
                .title(config.title())
                .size(config.windowSize())
                .isResizable(config.windowResizable())
                .build();
    }

    @Override
    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
    public CrowScreenManager getScreenManager() {
        return screenManager;
    }

    @Override
    public GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public AudioManager getAudioManager() {
        return audioManager;
    }

    @Override
    public void addGameObject(final GameObject go) {
        gameManager.addGameObject(go);
    }
}
