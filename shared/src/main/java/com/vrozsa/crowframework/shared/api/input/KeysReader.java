package com.vrozsa.crowframework.shared.api.input;

/**
 * Allows to read keys pressed in the keyboard.
 */
public interface KeysReader {
    /**
     * Get the next key typed by the player available. If there is no key in the cache, will wait until a key is typed.
     * @return the key typed.
     */
    InputKey getNext();

    /**
     * Clears the keys buffered in cache (keys typed by the player but not yet reacted by the system yet).
     */
    void clear();
}
