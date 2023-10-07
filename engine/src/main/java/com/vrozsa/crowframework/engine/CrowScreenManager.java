package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.game.component.StaticRenderer;
import com.vrozsa.crowframework.screen.api.SimpleScreen;
import com.vrozsa.crowframework.screen.api.WindowCloseRequestListener;
import com.vrozsa.crowframework.screen.internal.BaseView;
import com.vrozsa.crowframework.screen.internal.RendererView;
import com.vrozsa.crowframework.screen.internal.ScreenHandler;
import com.vrozsa.crowframework.screen.internal.ScreenHandlerConfig;
import com.vrozsa.crowframework.screen.ui.UIIcon;
import com.vrozsa.crowframework.screen.ui.UIIconTemplate;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.shared.api.screen.Screen;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;

class CrowScreenManager implements ScreenManager, OffsetGetter {
    private static final String DEFAULT_SCREEN = "DEFAULT_SCREEN";
    private static final String UI_VIEW = "UI_VIEW";
    private static final String RENDERER_VIEW = "RENDERER_VIEW";
    private static final String BASE_VIEW_GROUP = "DEFAULT_VIEW_GROUP";
    private final ScreenHandler screenHandler;

    CrowScreenManager(final Color bgColor, final ScreenHandlerConfig screenHandlerConfig, final InputHandler inputHandler) {
        screenHandler = new ScreenHandler(screenHandlerConfig, inputHandler);

        setup(bgColor);
    }

    private void setup(final Color bgColor) {
        var screenSize = screenHandler.getSize();

        var simpleScreen = new SimpleScreen(DEFAULT_SCREEN, screenSize.clone(), bgColor);

        var uiView = new BaseView(UI_VIEW, Rect.atOrigin(screenSize));
        simpleScreen.addView(uiView);

        var rendererView = new RendererView(RENDERER_VIEW, Rect.atOrigin(screenSize));
        simpleScreen.addView(rendererView);

        simpleScreen.addViewGroup(BASE_VIEW_GROUP, RENDERER_VIEW, UI_VIEW);
        simpleScreen.displayViewGroup(BASE_VIEW_GROUP);

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

    void update() {
        screenHandler.update();
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
                .getView(UI_VIEW);

        view.addComponent(icon);

        return icon;
    }

    @Override
    public void renderGO(final GameObject go) {
        var renderer = (RendererView)screenHandler.getScreen(DEFAULT_SCREEN)
                .getView(RENDERER_VIEW);

        renderer.addRenderer(go.getComponent(StaticRenderer.class));
    }
}
