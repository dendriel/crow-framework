package com.rozsa.crow.screen;

import com.rozsa.crow.screen.attributes.Size;

public class ScreenHandlerConfig {
    private String title;
    private Size size;
    private boolean isFullscreen;
    private boolean isVisible;
    private boolean isResizable;
    private boolean isAnimationsEnabled;
    private int animationsFPS;

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

    public boolean isAnimationsEnabled() {
        return isAnimationsEnabled;
    }

    public void setAnimationsEnabled(boolean animationsEnabled) {
        isAnimationsEnabled = animationsEnabled;
    }

    public int getAnimationsFPS() {
        return animationsFPS;
    }

    public void setAnimationsFPS(int animationsFPS) {
        this.animationsFPS = animationsFPS;
    }
}
