package com.vrozsa.crowframework.shared.api.game;

import com.vrozsa.crowframework.shared.attributes.Offset;

/**
 * Represents the position of a Game Object in the game scenario.
 * All positions contain X,Y,Z axis. X is horizontal axis; Y is vertical axis; Z is depth axis (used to decide what
 * is rendered first; what sprite is above).
 */
public interface PositionComponent {
    /**
     * Gets the horizontal position value.
     * @return the horizontal position.
     */
    int getX();

    /**
     * Gets the vertical position value.
     * @return the vertical position.
     */
    int getY();

    /**
     * Gets the depth position value.
     * @return the depth position.
     */
    int getZ();

    /**
     * Sets the horizontal and vertical position.
     * @param x the new horizontal position.
     * @param y the new vertical position.
     */
    void setPosition(int x, int y);

    /**
     * Add an offset to the position value.
     * @param offset offset to be added.
     */
    void addOffset(Offset offset);
}
