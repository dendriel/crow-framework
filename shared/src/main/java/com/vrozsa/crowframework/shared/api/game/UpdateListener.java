package com.vrozsa.crowframework.shared.api.game;

/**
 * Listens for updates.
 */
@FunctionalInterface
public interface UpdateListener {
    /**
     * Called when an update has occurred.
     */
    void onUpdate();
}
