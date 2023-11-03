package com.vrozsa.crowframework.screen.ui.listener;

/**
 * Set up a listener for UI events.
 */
@FunctionalInterface
public interface UIEventListener {
    /**
     * Invoked when the event happens.
     * @param state state to be passed back to the callback. For instance, could be a data object or the UI component
     *              itself.
     */
    void onEvent(Object state);
}
