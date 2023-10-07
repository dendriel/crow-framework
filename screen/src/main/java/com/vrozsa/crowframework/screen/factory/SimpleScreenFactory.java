package com.vrozsa.crowframework.screen.factory;

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

    public static BaseScreen create(final Size size, final String backgroundImageFile) {
        var simpleScreen = new SimpleScreen("xpto", size);

//        var rect = Rect.atOrigin(size);
//        var staticView = new StaticView(rect, backgroundImageFile);
//        simpleScreen.addView(SimpleViewType.STATIC, staticView);
//        simpleScreen.displayView(SimpleViewType.STATIC);

        logger.debug("New SimpleScreen created! %s", simpleScreen);

        return simpleScreen;
    }
}
