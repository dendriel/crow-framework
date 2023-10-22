package com.vrozsa.crowframework.shared.api.game;

/**
 * Allows to observe position changes.
 */
public interface PositionObserver {
    /**
     * Called when the position changes.
     * @param newPosX absolute X position.
     * @param newPosY absolute X position.
     */
    void positionChanged(int newPosX, int newPosY);
}
