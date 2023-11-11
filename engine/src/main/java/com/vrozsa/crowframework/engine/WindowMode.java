package com.vrozsa.crowframework.engine;

/**
 * Defines the window initial components.
 */
public enum WindowMode {
    /**
     * Basic screen and views (UI and renderer) are added by default.
     */
    DEFAULT,
    /**
     * Only a base screen is added by default, but no views.
     */
    DEFAULT_SCREEN,
    /**
     * No screen nor views are added by default.
     */
    RAW,
}
