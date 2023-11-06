package com.vrozsa.crowframework.screen.ui.components.api;

/**
 * Allows to set the fill of a fill-bar.
 */
public interface UIFiller {
    /**
     * Set slide bar fill.
     * @param fill the amount of fill the bar should have (between fill >= 0 and fill <= 1).
     */
    void setFill(float fill);
}
