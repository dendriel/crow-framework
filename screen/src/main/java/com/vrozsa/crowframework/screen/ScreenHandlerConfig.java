package com.vrozsa.crowframework.screen;

import com.vrozsa.crowframework.shared.attributes.Size;

public class ScreenHandlerConfig {
    private String title;
    private Size size;
    private boolean isFullscreen;
    private boolean isVisible;
    private boolean isResizable;
    private boolean terminateOnWindowCloseClick;

    public ScreenHandlerConfig() {
        isVisible = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isResizable() {
        return isResizable;
    }

    public void setResizable(boolean resizable) {
        isResizable = resizable;
    }

    public boolean isTerminateOnWindowCloseClick() {
        return terminateOnWindowCloseClick;
    }

    public void setTerminateOnWindowCloseClick(boolean terminateOnWindowCloseClick) {
        this.terminateOnWindowCloseClick = terminateOnWindowCloseClick;
    }
}
