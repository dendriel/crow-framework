package com.vrozsa.crowframework.shared.api.input;

import com.vrozsa.crowframework.shared.attributes.Offset;

/**
 * Observes a specific pointer event.
 */
public interface PointerObserver {

    /**
     * Triggered when the pointer event has occurred.
     * @param pos where in the game-screen the pointer event occurred.
     */
    void onPointerEvent(Offset pos);
}
