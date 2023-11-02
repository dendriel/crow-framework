package com.vrozsa.crowframework.screen;

/**
 * Listener for close action from game window.
 */
@FunctionalInterface
public interface WindowCloseRequestListener {
    /**
     * Invoked when the player closes the game window.
     */
    void onWindowClose();
}
