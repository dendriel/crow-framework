package com.vrozsa.crowframework.screen;

import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Builder;

/**
 * Base configuration for a Window Handler.
 * @param title window title. (default: 'Crow Game')
 * @param size window size. (default: 800x600)
 * @param isFullscreen the window will be launched in full-screen mode. (default: false)
 * @param isVisible window starts visible or hidden. (default: true)
 * @param isResizable the window is resizable or has a fixed size. (default: false)
 * @param terminateOnWindowCloseClick terminate the game when the player click the exit (x) window button? (default: true)
 */
@Builder
public record WindowHandlerConfig(
    String title,
    Size size,
    boolean isFullscreen,
    boolean isVisible,
    boolean isResizable,
    boolean terminateOnWindowCloseClick
) {
    public static class WindowHandlerConfigBuilder {
        private static final int DEFAULT_SCREEN_WIDTH = 800;
        private static final int DEFAULT_SCREEN_HEIGHT = 600;
        private String title = "Crow Game";
        private Size size = Size.of(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        private boolean isVisible = true;
        private boolean terminateOnWindowCloseClick = true;
    }
}
