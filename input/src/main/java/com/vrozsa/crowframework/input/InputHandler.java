package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.api.input.KeysListener;

import java.util.List;

/**
 * Handles user keyboard input.
 */
public interface InputHandler {
    /**
     * Creates a new buffered input handler.
     * <p>
     *     A buffered input handler allows to buffer the keys pressed by the player and to consume then when possible.
     *     For instance, if you have a rogue-like kind of game and each turn the player can issue a command. In this
     *     case, the player could decide its strategy and press many keys in advance and they would be buffered and
     *     automatically processed in the next few turns.
     * </p>
     * @return the new input handler instance.
     */
    static InputHandler createBuffered() {
        return BufferedInputHandler.create();
    }

    /**
     * Creates a new buffered input handler.
     * <p>
     *     A buffered input handler allows to buffer the keys pressed by the player and to consume then when possible.
     *     For instance, if you have a rogue-like kind of game and each turn the player can issue a command. In this
     *     case, the player could decide its strategy and press many keys in advance and they would be buffered and
     *     automatically processed in the next few turns.
     * </p>
     * @param bufferSize the maximum amount of inputs to buffer.
     * @return the new input handler instance.
     */
    static InputHandler createBuffered(int bufferSize) {
        return BufferedInputHandler.create(bufferSize);
    }

    /**
     * Creates a tracking input handler.
     * <p>
     *     A tracking input handler allows to check which keys are pressed by the player each moment. It is ideal for
     *     arcade games in which the game can react to player's input in each frame.
     * </p>
     * @return the new input handler instance.
     */
    static InputHandler createTracking() {
        return TrackingInputHandler.create();
    }

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
