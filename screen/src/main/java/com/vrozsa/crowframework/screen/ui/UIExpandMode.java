package com.vrozsa.crowframework.screen.ui;

/**
 * Defines the behavior of UI Components when resizing the window.
 */
public enum UIExpandMode {
    /**
     * UI components won't resize with the screen.
     */
    NONE,
    /**
     * UI components will try to resize accordingly with the new screen size.
     */
    FILL,
}
