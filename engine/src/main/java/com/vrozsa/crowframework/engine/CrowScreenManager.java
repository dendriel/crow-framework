package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.game.component.collider.ColliderGizmosRenderer;
import com.vrozsa.crowframework.screen.WindowHandler;
import com.vrozsa.crowframework.screen.WindowHandlerConfig;
import com.vrozsa.crowframework.screen.ui.components.UIIcon;
import com.vrozsa.crowframework.screen.ui.components.UILabel;
import com.vrozsa.crowframework.screen.ui.components.button.UIButton;
import com.vrozsa.crowframework.screen.ui.components.templates.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIIconTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UILabelTemplate;
import com.vrozsa.crowframework.screen.views.RendererView;
import com.vrozsa.crowframework.screen.views.UIView;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.input.KeysListener;
import com.vrozsa.crowframework.shared.api.input.PointerListener;
import com.vrozsa.crowframework.shared.api.screen.OffsetGetter;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.api.screen.Screen;
import com.vrozsa.crowframework.shared.api.screen.View;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

/**
 * Manager of screen-related components.
 */
class CrowScreenManager implements ScreenManager, OffsetGetter {
    private static final String DEFAULT_SCREEN = "DEFAULT_SCREEN";
    private static final String BASE_VIEW_GROUP = "DEFAULT_VIEW_GROUP";
    private final WindowHandler windowHandler;
    private final boolean showGizmos;
    private final WindowMode windowMode;

    CrowScreenManager(WindowMode windowMode, boolean showGizmos, WindowHandlerConfig windowHandlerConfig) {
        windowHandler = WindowHandler.create(windowHandlerConfig);
        this.windowMode = windowMode;
        this.showGizmos = showGizmos;
    }

    /**
     * Set up the screens.
     * @param bgColor frame background color.
     */
    public void setup(final Color bgColor) {
        windowHandler.initialize();
        var screenSize = windowHandler.getSize();

        if (windowMode == WindowMode.RAW) {
            return;
        }

        // As this is a generic engine, we allow views to be added from outside the screen.
        // Otherwise, the AbstractScreen should be extended and add the views by itself.
        var screen = new SimpleScreen(DEFAULT_SCREEN, screenSize.clone(), bgColor);

        if (windowMode == WindowMode.DEFAULT) {
            var uiView = new UIView(Rect.atOrigin(screenSize));
            screen.addView(uiView);

            var rendererView = new RendererView(Rect.atOrigin(screenSize));
            screen.addView(rendererView);

            screen.addViewGroup(BASE_VIEW_GROUP, UIView.DEFAULT_UI_VIEW, RendererView.DEFAULT_RENDERER_VIEW);
            screen.displayViewGroup(BASE_VIEW_GROUP);
        }

        addScreen(screen);
        setOnlyScreenVisible(screen.getName(), true);
    }

    public void setupInputListeners(KeysListener keysListener, PointerListener pointerListener) {
        windowHandler.setupKeysListener(keysListener);
        windowHandler.setupPointerListener(pointerListener);
    }

    @Override
    public Offset getOffset() {
        return windowHandler.getPosition();
    }

    @Override
    public void addScreen(final Screen screen) {
        windowHandler.addScreen(screen);
    }

    void setOnlyScreenVisible(final String name, final boolean isVisible) {
        windowHandler.setScreenVisible(name, isVisible);
    }

    void update() {
        windowHandler.update();
    }

    void show() {
        windowHandler.setVisible(true);
    }

    void hide() {
        windowHandler.setVisible(false);
    }

    void terminate() {
        windowHandler.terminate();
    }

    @Override
    public Size getSize() {
        return windowHandler.getSize();
    }

    @Override
    public RendererView getRendererView() {
        return (RendererView) windowHandler
                .getScreen(DEFAULT_SCREEN)
                .getView(RendererView.DEFAULT_RENDERER_VIEW);
    }

    @Override
    public void addView(View view) {
        var defaultScreen = (SimpleScreen)windowHandler.getScreen(DEFAULT_SCREEN);
        defaultScreen.addView(view);
        defaultScreen.draw();
    }

    @Override
    public UIButton createButton(UIButtonTemplate template) {
        return (UIButton) getUIView().createComponent(template);
    }

    @Override
    public UIIcon createIcon(UIIconTemplate template) {
        return (UIIcon) getUIView().createComponent(template);
    }

    @Override
    public UILabel createLabel(UILabelTemplate template) {
        return (UILabel) getUIView().createComponent(template);
    }

    private UIView getUIView() {
        return (UIView)windowHandler.getScreen(DEFAULT_SCREEN)
                .getView(UIView.DEFAULT_UI_VIEW);
    }

    @Override
    public void renderGO(final GameObject go) {
        var renderer = (RendererView) windowHandler.getScreen(DEFAULT_SCREEN)
                .getView(RendererView.DEFAULT_RENDERER_VIEW);

        var renderers = go.getAllComponents(Renderer.class);

        if (!showGizmos) {
            renderers = renderers.stream()
                    .filter(r -> !(r instanceof ColliderGizmosRenderer))
                    .toList();
        }

        renderer.addRenderer(renderers);
    }
}
