package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.game.RunnableGameLoop;
import com.vrozsa.crowframework.input.InputHandler;
import com.vrozsa.crowframework.input.PointerHandler;
import com.vrozsa.crowframework.screen.internal.ScreenHandlerConfig;
import com.vrozsa.crowframework.shared.api.game.GameLoop;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.attributes.Size;

/**
 * Crow Engine aimed to be an out-of-box engine to easily build games using Crow Framework.
 */
class Engine implements CrowEngine {
    private final CrowEngineConfig config;
    private final CrowInputManager inputManager;
    private final CrowScreenManager screenManager;
    private final CrowGameManager gameManager;
    private final CrowAudioManager audioManager;
    private final GameLoop gameLoop;

    Engine(final CrowEngineConfig config) {
        this.config = config;
        screenManager = setupScreenManager();
        inputManager = new CrowInputManager(screenManager);
        gameManager = new CrowGameManager(screenManager);
        audioManager = new CrowAudioManager(config.assetsPath());

        var inputHandler = inputManager.getInputHandler();
        var pointerHandler = inputManager.getPointerHandler();
        screenManager.setupInputListeners(inputHandler.asKeysListener(), pointerHandler.asPointerListener());

        gameLoop = RunnableGameLoop.get();
        gameLoop.setScreenUpdateListener(screenManager::update);
        gameLoop.setCollisionUpdateListener(gameManager::handleCollision);
        gameLoop.addEarlyUpdateListener(gameManager::earlyUpdate);
        gameLoop.addUpdateListener(gameManager::update);
        gameLoop.addLateUpdateListener(gameManager::lateUpdate);

        gameLoop.start();
    }

    private CrowScreenManager setupScreenManager() {
        var screenHandlerConfig = ScreenHandlerConfig.builder()
                .title(config.title())
                .size(Size.of(config.screenWidth(), config.screenHeight()))
                .build();
        return new CrowScreenManager(config.color(), config.showGizmos(), screenHandlerConfig);
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
