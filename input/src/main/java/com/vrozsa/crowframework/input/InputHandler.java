package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.api.input.KeysListener;

import java.util.List;

/**
 * Handles user keyboard input.
 */
public interface InputHandler {

    /**
     * Get the next key typed by the user available. If there is no key in the cache, will wait until a key is typed.
     * @return the key typed.
     */
    InputKey getNext();

    /**
     * Clears the keys buffered in cache (keys typed by the user but not reacted by the system yet).
     */
    void clear();

    /**
     * Get the keys currently being pressed.
     * @return the keys pressed right now.
     */
    List<InputKey> getPressedKeys();

    /**
     * Blocks until some of the expected keys are typed.
     * @param keys target keys to be read.
     */
    void readUntil(final InputKey...keys);

    /**
     * Gets the keys listener component of the input handler.
     * <p>
     *     Used internally to link the input handler with the input channels.
     * </p>
     */
    KeysListener asKeysListener();
}
