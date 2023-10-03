package com.vrozsa.crowframework.screen.builder;

import com.vrozsa.crowframework.screen.api.ScreenType;
import com.vrozsa.crowframework.screen.internal.ScreenHandler;
import com.vrozsa.crowframework.screen.internal.ScreenHandlerConfig;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScreenHandlerFactory {
    private static final LoggerService logger = LoggerService.of(ScreenHandlerFactory.class);

    public static ScreenHandler<ScreenType> createWithSimpleScreen(final String backgroundImageFile) {
        return createWithSimpleScreen(backgroundImageFile, true);
    }

    public static ScreenHandler<ScreenType> createWithSimpleScreen(final String backgroundImageFile, final boolean isVisible) {
        ScreenHandler<ScreenType> screenHandler = create(ScreenType.class, isVisible);

        var simpleScreen = SimpleScreenFactory.create(screenHandler.getSize(), backgroundImageFile);

        screenHandler.add(ScreenType.SIMPLE, simpleScreen);
        screenHandler.setOnlyVisible(ScreenType.SIMPLE, true);

        return screenHandler;
    }

    public static ScreenHandler<ScreenType> create() {
        return create(ScreenType.class, true);
    }

    public static <T extends Enum<T>> ScreenHandler<T> create(final Class<T> type, final boolean isVisible) {
        ScreenHandlerConfig config = ScreenHandlerConfig.builder()
                .title("Main Test Window")
                .size(Size.of(800, 600))
                .isVisible(false)
                .isFullscreen(false)
                .terminateOnWindowCloseClick(true)
                .build();

        ScreenHandler<T> screenHandler = new ScreenHandler<>(config);
        screenHandler.setVisible(isVisible);

        logger.debug("New screen handler created! %s", screenHandler);

        return screenHandler;
    }
}
