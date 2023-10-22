package com.vrozsa.crowframework.shared.api.game;

/**
 * Allows to observe the addition of offsets to a position.
 */
public interface PositionOffsetObserver {

    /**
     * Called when the position is changed by adding offset.
     * @param offsetX offset X added.
     * @param offsetY offset Y added.
     */
    void offsetAdded(int offsetX, int offsetY);
}
