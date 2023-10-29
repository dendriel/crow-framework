package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.game.component.collider.ColliderGizmosRenderer;
import com.vrozsa.crowframework.screen.api.SimpleScreen;
import com.vrozsa.crowframework.screen.api.WindowCloseRequestListener;
import com.vrozsa.crowframework.screen.internal.RendererView;
import com.vrozsa.crowframework.screen.internal.UIView;
import com.vrozsa.crowframework.screen.internal.WindowHandler;
import com.vrozsa.crowframework.screen.internal.WindowHandlerConfig;
import com.vrozsa.crowframework.screen.ui.UIFontTemplate;
import com.vrozsa.crowframework.screen.ui.UIIcon;
import com.vrozsa.crowframework.screen.ui.UIIconTemplate;
import com.vrozsa.crowframework.screen.ui.UILabel;
import com.vrozsa.crowframework.screen.ui.UILabelTemplate;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.input.KeysListener;
import com.vrozsa.crowframework.shared.api.input.PointerListener;
import com.vrozsa.crowframework.shared.api.screen.OffsetGetter;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.api.screen.Screen;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;

class CrowScreenManager implements ScreenManager, OffsetGetter {
    private static final String DEFAULT_SCREEN = "DEFAULT_SCREEN";
    private static final String BASE_VIEW_GROUP = "DEFAULT_VIEW_GROUP";
    private final WindowHandler windowHandler;
    private final boolean showGizmos;

    CrowScreenManager(final boolean showGizmos, final WindowHandlerConfig windowHandlerConfig) {
        windowHandler = WindowHandler.create(windowHandlerConfig);
        this.showGizmos = showGizmos;
    }

    /**
     * Setup the screens.
     * @param bgColor frame background color.
     */
    public void setup(final Color bgColor) {
        windowHandler.setup();
        var screenSize = windowHandler.getSize();

        var simpleScreen = new SimpleScreen(DEFAULT_SCREEN, screenSize.clone(), bgColor);

        var uiView = new UIView(Rect.atOrigin(screenSize));
        simpleScreen.addView(uiView);

        var rendererView = new RendererView(Rect.atOrigin(screenSize));
        simpleScreen.addView(rendererView);

        simpleScreen.addViewGroup(BASE_VIEW_GROUP, UIView.DEFAULT_UI_VIEW, RendererView.DEFAULT_RENDERER_VIEW);
        simpleScreen.displayViewGroup(BASE_VIEW_GROUP);

        addScreen(simpleScreen);
        setOnlyScreenVisible(simpleScreen.name(), true);
    }

    public void setupInputListeners(KeysListener keysListener, PointerListener pointerListener) {
        windowHandler.setupKeysListener(keysListener);
        windowHandler.setupPointerListener(pointerListener);
    }

    @Override
    public Offset getOffset() {
        return windowHandler.getPosition();
    }

    void addWindowCloseRequestListener(WindowCloseRequestListener listener) {
        windowHandler.addOnWindowCloseRequestListener(listener);
    }

    void addScreen(final Screen screen) {
        windowHandler.add(screen.name(), screen);
    }

    void setOnlyScreenVisible(final String name, final boolean isVisible) {
        windowHandler.setOnlyScreenVisible(name, isVisible);
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

    void close() {
        windowHandler.exit();
    }

    public RendererView getRendererView() {
        return (RendererView) windowHandler
                .getScreen(DEFAULT_SCREEN)
                .getView(RendererView.DEFAULT_RENDERER_VIEW);
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
        getUIView().addComponent(icon);

        return icon;
    }

    public UILabel addLabel(final String text, final int size, final Rect rect) {
        var uiFontTemplate = new UIFontTemplate();
        uiFontTemplate.setSize(size);

        var labelTemplate = new UILabelTemplate();
        labelTemplate.setText(text);
        labelTemplate.setRect(rect);
        labelTemplate.setFont(uiFontTemplate);
        var label = UILabel.from(labelTemplate);
        getUIView().addComponent(label);

        return label;
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
