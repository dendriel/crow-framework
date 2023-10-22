package com.vrozsa.crowframework.screen.internal;

import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Builder;

@Builder
public record ScreenHandlerConfig(
    String title,
    Size size,
    boolean isFullscreen,
    boolean isVisible,
    boolean isResizable,
    boolean terminateOnWindowCloseClick
) {
    public static class ScreenHandlerConfigBuilder {
        private static final int DEFAULT_SCREEN_WIDTH = 800;
        private static final int DEFAULT_SCREEN_HEIGHT = 600;
        private String title = "Crow Game";
        private Size size = Size.of(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        private boolean isVisible = true;
        private boolean terminateOnWindowCloseClick = true;
    }
}
