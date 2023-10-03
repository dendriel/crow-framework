package com.vrozsa.crowframework.screen.internal;

import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScreenHandlerConfig {
    private String title;
    private Size size;
    private boolean isFullscreen;
    private boolean isVisible = true;
    private boolean isResizable;
    private boolean terminateOnWindowCloseClick;
}
