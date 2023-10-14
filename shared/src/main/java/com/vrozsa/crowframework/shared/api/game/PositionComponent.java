package com.vrozsa.crowframework.shared.api.game;

import com.vrozsa.crowframework.shared.attributes.Offset;

import java.util.List;

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
     * Gets the offset of this position (x and y).
     * @return the position offset.
     */
    Offset getOffset();

    /**
     * Sets the offset part of this position.
     * @param offset the new position offset.
     */
    void setOffset(Offset offset);

    /**
     * Gets the absolute X position. If this position is attached to a parent, this will be the sum of the parent absolute
     * position with the current relative position of the object.
     *
     * For instance, if the current object position is X=50 and Y=45, but its parent has a absolute position of X=300 and Y=500,
     * the absolute position of the child will be X=350 and Y=545.
     *
     * @return the absolute X position.
     */
    int getAbsolutePosX();

    /**
     * Gets the absolute Y position. If this position is attached to a parent, this will be the sum of the parent absolute
     * position with the current relative position of the object.
     * @return the absolute Y position.
     */
    int getAbsolutePosY();

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

    /**
     * Add an observer for changes in the offset.
     * @param observer the observer callback.
     */
    void addPositionOffsetAddedListener(PositionOffsetObserver observer);

    /**
     * Set the parent position component. The child position will be attached to the parent.
     * @param parent the new parent position.
     */
    void setParent(PositionComponent parent);

    /**
     * Get a list of children positions attached to this position.
     * @return the list of children positions.
     */
    List<PositionComponent> getChildren();

    /**
     * Gets the game object in which this position is attached.
     * @return the game object that owns this position.
     */
    GameObject getGameObject();
}
