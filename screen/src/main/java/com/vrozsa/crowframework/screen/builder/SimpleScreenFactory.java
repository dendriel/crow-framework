package com.vrozsa.crowframework.screen.builder;

import com.vrozsa.crowframework.screen.api.SimpleScreen;
import com.vrozsa.crowframework.screen.api.SimpleViewType;
import com.vrozsa.crowframework.screen.api.StaticView;
import com.vrozsa.crowframework.screen.internal.BaseScreen;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleScreenFactory {
    private static final LoggerService logger = LoggerService.of(SimpleScreenFactory.class);

    public static BaseScreen<SimpleViewType, SimpleViewType> create(final Size size, final String backgroundImageFile) {
        SimpleScreen simpleScreen = new SimpleScreen(size);

        Rect rect = Rect.atOrigin(size);
        StaticView staticView = new StaticView(rect, backgroundImageFile);
        simpleScreen.addView(staticView);
        simpleScreen.displayView();

        logger.debug("New SimpleScreen created! %s", simpleScreen);

        return simpleScreen;
    }
}
