package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.shared.attributes.Color;

/**
 * Crow Engine public API.
 */
public interface CrowEngine {

    static CrowEngine create() {
        var config = CrowEngineConfig.builder().build();
        return create(config);
    }

    static CrowEngine create(final int screenWidth, final int screenHeight, final Color color) {
        var config = CrowEngineConfig.builder()
                .screenWidth(screenWidth)
                .screenHeight(screenHeight)
                .color(color)
                .build();
        return create(config);
    }

    static CrowEngine create(final CrowEngineConfig config) {
        return new SimpleCrowEngine(config);
    }

    InputManager getInputManager();
}
