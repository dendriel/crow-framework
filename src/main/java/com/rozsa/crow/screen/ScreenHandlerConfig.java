package com.rozsa.crow.screen;

import com.rozsa.crow.screen.attributes.Size;

public class ScreenHandlerConfig {
    private String title;
    private Size size;
    private boolean isFullscreen;
    private boolean isVisible;
    private boolean isResizable;

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
}
