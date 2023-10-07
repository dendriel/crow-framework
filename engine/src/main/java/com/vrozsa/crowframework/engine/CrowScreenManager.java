package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.screen.api.SimpleScreen;
import com.vrozsa.crowframework.screen.api.WindowCloseRequestListener;
import com.vrozsa.crowframework.screen.internal.BaseView;
import com.vrozsa.crowframework.screen.internal.ScreenHandler;
import com.vrozsa.crowframework.screen.internal.ScreenHandlerConfig;
import com.vrozsa.crowframework.screen.ui.UIIcon;
import com.vrozsa.crowframework.screen.ui.UIIconTemplate;
import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.shared.api.screen.Screen;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;

class CrowScreenManager implements ScreenManager, OffsetGetter {
    private static final String DEFAULT_SCREEN = "DEFAULT_SCREEN";
    private static final String DEFAULT_VIEW = "DEFAULT_VIEW";
    private final ScreenHandler screenHandler;

    CrowScreenManager(final Color bgColor, final ScreenHandlerConfig screenHandlerConfig, final InputHandler inputHandler) {
        screenHandler = new ScreenHandler(screenHandlerConfig, inputHandler);

        setup(bgColor);
    }

    private void setup(final Color bgColor) {
        var screenSize = screenHandler.getSize();

        var simpleScreen = new SimpleScreen(DEFAULT_SCREEN, screenSize.clone(), bgColor);

        var view = new BaseView(DEFAULT_VIEW, Rect.atOrigin(screenSize));
        simpleScreen.addView(view);

        addScreen(simpleScreen);
        setOnlyScreenVisible(simpleScreen.name(), true);
    }

    @Override
    public Offset getOffset() {
        return screenHandler.getPosition();
    }

    void addWindowCloseRequestListener(WindowCloseRequestListener listener) {
        screenHandler.addOnWindowCloseRequestListener(listener);
    }

    void addScreen(final Screen screen) {
        screenHandler.add(screen.name(), screen);
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

    @Override
    public UIIcon addIcon(final String imageFile, final int x, final int y, final int width, final int height) {
        return addIcon(imageFile, Rect.of(x, y, width, height));
    }

    @Override
    public UIIcon addIcon(final String imageFile, final Rect rect) {
        var iconTemplate = UIIconTemplate.builder()
                .imageFile(imageFile)
                .rect(rect)
                .referenceSize(rect.getSize())
                .build();

        var icon = UIIcon.from(iconTemplate);
        var view = screenHandler.getScreen(DEFAULT_SCREEN)
                .getView(DEFAULT_VIEW);

        view.addComponent(icon);

        return icon;
    }
}
