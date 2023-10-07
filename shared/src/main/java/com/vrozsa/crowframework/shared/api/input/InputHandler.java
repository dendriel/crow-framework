package com.vrozsa.crowframework.shared.api.input;

import java.awt.event.KeyListener;
import java.util.List;

/**
 * Handles user keyboard input.
 */
public interface InputHandler extends KeyListener {
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
     * Get all keys pressed right now.
     * @return the keys that are being pressed.
     */
    List<InputKey> getPressedKeys();

    /**
     * Blocks until some of the expected keys are typed.
     * @param keys target keys to be read.
     */
    void readUntil(final InputKey...keys);
}
