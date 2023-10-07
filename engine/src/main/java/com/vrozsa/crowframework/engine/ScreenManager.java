package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.screen.api.ScreenType;
import com.vrozsa.crowframework.screen.api.WindowCloseRequestListener;
import com.vrozsa.crowframework.screen.internal.ScreenHandler;
import com.vrozsa.crowframework.shared.attributes.Offset;

class ScreenManager implements OffsetGetter {
//    private final ScreenManagerConfig config;
    private ScreenHandler<ScreenType> screenHandler;

    ScreenManager(
//            final ScreenManagerConfig config,
            SimpleInputManager inputManager
    ) {
//        this.config = config;

//        setup(config.getScreenHandlerConfig(), inputManager);
    }

//    private void setup(ScreenHandlerConfig config, InputManager inputManager) {
//        screenHandler = new ScreenHandler<>(config, inputManager.getInputHandler());
//        Size size = screenHandler.getSize();
//
//        screenHandler.add(ScreenType.LOGIN, new LoginScreen(configLoader.getConfig(LoginScreenConfig.class), size));
//        screenHandler.add(ScreenType.GAME, new GameScreen(configLoader.getConfig(GameScreenConfig.class), this, size, configLoader.getSysConfig().getTileSize()));
//    }

    @Override
    public Offset getOffset() {
        return screenHandler.getPosition();
    }

    void addWindowCloseRequestListener(WindowCloseRequestListener listener) {
        screenHandler.addOnWindowCloseRequestListener(listener);
    }

    void show() {
        screenHandler.setVisible(true);
    }

    void hide() {
        screenHandler.setVisible(false);
    }

    void close() {
        screenHandler.exit();
    }
}
