package com.vrozsa.crowframework.shared.api.input;

/**
 * Listen to keys events.
 */
public interface KeysListener {
    /**
     * Called when a key has been typed.
     * @param key key typed.
     */
    void onKeyTyped(InputKey key);

    /**
     * Called when a key has been pressed.
     * @param key key pressed.
     */
    void onKeyPressed(InputKey key);

    /**
     * Called when a key has been released.
     * @param key key released.
     */
    void onKeyReleased(InputKey key);
}
