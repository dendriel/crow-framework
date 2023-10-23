package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.attributes.Offset;

/**
 * Handles mouse input.
 */
public interface PointerHandler {

    /**
     * Creates a new PointerHandler.
     * @return the new pointer handler instance.
     */
    static PointerHandler create() {
        return MouseHandler.create();
    }

    /**
     * Gets the current pointer position in the screen.
     * @return the current pointer position.
     */
    Offset getPointerPosition();
}
