package com.vrozsa.crowframework.screen.builder;

import com.vrozsa.crowframework.screen.api.ScreenType;
import com.vrozsa.crowframework.screen.internal.ScreenHandler;
import com.vrozsa.crowframework.screen.internal.ScreenHandlerConfig;
import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScreenHandlerFactory {
    private static final LoggerService logger = LoggerService.of(ScreenHandlerFactory.class);

    public static ScreenHandler<ScreenType> createWithSimpleScreen(final String backgroundImageFile, final InputHandler inputHandler) {
        return createWithSimpleScreen(backgroundImageFile, true, inputHandler);
    }

    public static ScreenHandler<ScreenType> createWithSimpleScreen(final String backgroundImageFile, final boolean isVisible) {
        return createWithSimpleScreen(backgroundImageFile, isVisible, null);
    }

    public static ScreenHandler<ScreenType> createWithSimpleScreen(final String backgroundImageFile) {
        return createWithSimpleScreen(backgroundImageFile, true, null);
    }

    public static ScreenHandler<ScreenType> createWithSimpleScreen(
            final String backgroundImageFile,
            final boolean isVisible,
            final InputHandler inputHandler
    ) {
        ScreenHandler<ScreenType> screenHandler = create(ScreenType.class, isVisible, inputHandler);

        var simpleScreen = SimpleScreenFactory.create(screenHandler.getSize(), backgroundImageFile);

        screenHandler.add(ScreenType.SIMPLE, simpleScreen);
        screenHandler.setOnlyVisible(ScreenType.SIMPLE, true);

        return screenHandler;
    }

    public static ScreenHandler<ScreenType> create() {
        return create(ScreenType.class, true, null);
    }

    public static <T extends Enum<T>> ScreenHandler<T> create(final Class<T> type, final boolean isVisible, final InputHandler inputHandler) {
        ScreenHandlerConfig config = ScreenHandlerConfig.builder()
                .title("Main Test Window")
                .size(Size.of(800, 600))
                .isVisible(false)
                .isFullscreen(false)
                .terminateOnWindowCloseClick(true)
                .build();

        ScreenHandler<T> screenHandler = new ScreenHandler<>(config, inputHandler);
        screenHandler.setVisible(isVisible);

        logger.debug("New screen handler created! %s", screenHandler);

        return screenHandler;
    }
}
