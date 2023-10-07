package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.screen.api.WindowCloseRequestListener;
import com.vrozsa.crowframework.screen.internal.BaseScreen;
import com.vrozsa.crowframework.screen.internal.ScreenHandler;
import com.vrozsa.crowframework.screen.internal.ScreenHandlerConfig;
import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.shared.attributes.Offset;

class ScreenManager implements OffsetGetter {
    private final ScreenHandler screenHandler;

    ScreenManager(final ScreenHandlerConfig screenHandlerConfig, final InputHandler inputHandler) {
        screenHandler = new ScreenHandler(screenHandlerConfig, inputHandler);
    }

    @Override
    public Offset getOffset() {
        return screenHandler.getPosition();
    }

    void addWindowCloseRequestListener(WindowCloseRequestListener listener) {
        screenHandler.addOnWindowCloseRequestListener(listener);
    }

    void addScreen(final String name, final BaseScreen screen) {
        screenHandler.add(name, screen);
    }

    void setOnlyScreenVisible(final String name, final boolean isVisible) {
        screenHandler.setOnlyScreenVisible(name, isVisible);
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
