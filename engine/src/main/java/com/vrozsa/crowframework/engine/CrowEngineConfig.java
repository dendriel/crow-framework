package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Builder;

/**
 * Create a new Crow Engine configuration.
 * @param windowSize the size of the game window.
 * @param showGizmos show debugging gizmos.
 * @param color background color (only visible in the partes where is no image being rendered).
 * @param title game title to be displayed in the game window.
 * @param assetsPath base assets path to be used when loading resources (audio clip; images).
 * @param windowResizable the game window can be resized?
 */
@Builder
public record CrowEngineConfig(
        Size windowSize,
        boolean showGizmos,
        Color color,
        String title,
        String assetsPath,
        boolean windowResizable
) {
    private static final int DEFAULT_SCREEN_WIDTH = 800;
    private static final int DEFAULT_SCREEN_HEIGHT = 600;

    // Defaults for lombok
    public static class CrowEngineConfigBuilder {
        private Size windowSize = Size.of(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        private boolean windowResizable = false;
        private Color color = Color.blue();

        private String assetsPath = "/assets";
    }
}
