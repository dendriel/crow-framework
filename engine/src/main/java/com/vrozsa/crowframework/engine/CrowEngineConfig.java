package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.shared.attributes.Color;
import lombok.Builder;

@Builder
public record CrowEngineConfig(
        int screenWidth,
        int screenHeight,
        boolean showGizmos,
        Color color
) {
    private static final int DEFAULT_SCREEN_WIDTH = 800;
    private static final int DEFAULT_SCREEN_HEIGHT = 600;

    // Defaults for lombok
    public static class CrowEngineConfigBuilder {
        private int screenWidth = DEFAULT_SCREEN_WIDTH;
        private int screenHeight = DEFAULT_SCREEN_HEIGHT;
        private Color color = Color.blue();
    }
}
