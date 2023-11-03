package com.vrozsa.crowframework.screen.ui.components.api;

/**
 * Represents the handler in components that have one.
 */
public interface UIHandler {
    /**
     * Enable or disable the handler part of the componet.
     * @param enabled true=enabled; false=disabled.
     */
    void setHandlerEnabled(boolean enabled);
}
