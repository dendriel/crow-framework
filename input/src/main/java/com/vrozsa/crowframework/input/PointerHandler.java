package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.api.screen.OffsetGetter;
import com.vrozsa.crowframework.shared.attributes.Offset;

/**
 * Handles mouse input.
 */
public interface PointerHandler {

    /**
     * Creates a new PointerHandler.
     * @param screenOffset the screen offset getter, so it's possible to calculate the pointer position relative to the
     *                     game screen.
     * @return the new pointer handler instance.
     */
    static PointerHandler create(OffsetGetter screenOffset) {
        return MouseHandler.create(screenOffset);
    }

    /**
     * Gets the current pointer position in the game screen.
     * <p>
     *     This position is relative to the game screen.
     * </p>
     * @return the current pointer position.
     */
    Offset getPointerPosition();

    /**
     * Gets the current pointer absolute position in the screen.
     * <p>
     *      This position is relative to the player's screen.
     * </p>
     * @return the current pointer position.
     */
    Offset getPointerAbsolutePosition();
}
