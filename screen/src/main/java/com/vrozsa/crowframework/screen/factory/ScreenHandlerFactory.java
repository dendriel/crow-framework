package com.vrozsa.crowframework.screen.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScreenHandlerFactory {
    /*
    private static final LoggerService logger = LoggerService.of(ScreenHandlerFactory.class);

    public static ScreenHandler createWithSimpleScreen(final String backgroundImageFile, final InputHandler inputHandler) {
        return createWithSimpleScreen(backgroundImageFile, true, inputHandler);
    }

    public static ScreenHandler createWithSimpleScreen(final String backgroundImageFile, final boolean isVisible) {
        return createWithSimpleScreen(backgroundImageFile, isVisible, null);
    }

    public static ScreenHandler createWithSimpleScreen(final String backgroundImageFile) {
        return createWithSimpleScreen(backgroundImageFile, true, null);
    }

    public static ScreenHandler createWithSimpleScreen(
            final String backgroundImageFile,
            final boolean isVisible,
            final InputHandler inputHandler
    ) {
        var screenHandler = create(isVisible, inputHandler);

        var simpleScreen = SimpleScreenFactory.create(screenHandler.getSize(), backgroundImageFile);

        screenHandler.add(ScreenType.SIMPLE.name(), simpleScreen);
        screenHandler.setOnlyScreenVisible(ScreenType.SIMPLE.name(), true);

        return screenHandler;
    }

    public static ScreenHandler create() {
        return create(true, null);
    }

    public static ScreenHandler create(final boolean isVisible, final InputHandler inputHandler) {
        ScreenHandlerConfig config = ScreenHandlerConfig.builder()
                .title("Main Test Window")
                .size(Size.of(800, 600))
                .isVisible(false)
                .isFullscreen(false)
                .terminateOnWindowCloseClick(true)
                .build();

        // missing game loop
//        , StandAloneGameLoop.getInstance()
        var screenHandler = new ScreenHandler(config, inputHandler);
        screenHandler.setVisible(isVisible);

        logger.debug("New screen handler created! {}", screenHandler);

        return screenHandler;
    }
     */
}
