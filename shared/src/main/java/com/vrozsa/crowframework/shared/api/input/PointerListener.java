package com.vrozsa.crowframework.shared.api.input;

import com.vrozsa.crowframework.shared.attributes.Offset;

/**
 * Listen to pointer events.
 */
public interface PointerListener {
    /**
     * Called when there was a pointer click.
     * @param pos game screen click position.
     */
    void onPointerClicked(Offset pos);

    /**
     * Called when the pointer was pressed.
     * @param pos game screen press position.
     */
    void onPointerPressed(Offset pos);

    /**
     * Called when the pointer was released.
     * @param pos game screen release position.
     */
    void onPointerReleased(Offset pos);
}
