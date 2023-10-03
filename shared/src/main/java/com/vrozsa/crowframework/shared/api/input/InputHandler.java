package com.vrozsa.crowframework.shared.api.input;

import java.awt.event.KeyListener;

public interface InputHandler extends KeyListener {

    /**
     * Get the next key typed by the user available. If there is no key in the cache, will wait until a key is typed.
     * @return the key typed.
     */
    InputKey getNext();

    /**
     * Clears the keys buffered in cache (keys typed by the user but not reacted by the system yet).
     */
    void clearCache();
}
