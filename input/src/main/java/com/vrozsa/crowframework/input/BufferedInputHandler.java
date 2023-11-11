package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.api.input.KeysListener;
import com.vrozsa.crowframework.shared.api.input.KeysReader;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Reads and buffers typed keys until read.
 * <p>
 *     This kind of input handler may be useful if we want to track all player commands and reproduce it sequentially one
 *     action after another (so the player types the commands and sees the result progressively).
 * </p>
 * <p>
 *     WARNING: Must be attached to a screen component as a KeysListener.
 * </p>
 */
final class BufferedInputHandler implements InputHandler, KeysListener, KeysReader {
    private static final LoggerService logger = LoggerService.of(BufferedInputHandler.class);
    private static final int MAX_CACHED_INPUTS = 5;

    // Uses a concurrent-able version because both the game-loop and input-thread will be accessing this list.
    private final LinkedBlockingQueue<InputKey> inputs;
    private boolean isKeyReleased = true;

    private BufferedInputHandler() {
        inputs = new LinkedBlockingQueue<>(MAX_CACHED_INPUTS);
    }

    private BufferedInputHandler(int bufferSize) {
        inputs = new LinkedBlockingQueue<>(bufferSize);
    }

    public static BufferedInputHandler create() {
        return new BufferedInputHandler();
    }

    public static BufferedInputHandler create(int bufferSize) {
        return new BufferedInputHandler(bufferSize);
    }

    public List<InputKey> getPressedKeys() {
        return List.copyOf(inputs);
    }

    public void readUntil(final InputKey... keys) {
        List<InputKey> targetKeys = List.of(keys);
        if (targetKeys.isEmpty()) {
            logger.warn("[readUntil] Received an empty key set to wait for.");
            return;
        }

        InputKey nextInput;
        do {
            try {
                nextInput = inputs.take();
            }
            catch (InterruptedException e) {
                logger.warn("[readUntil] Current thread was interrupted! %s", e.toString());
                nextInput = InputKey.UNKNOWN;
            }
        } while (!targetKeys.contains(nextInput));
    }

    public InputKey getNext() {
        try {
            return inputs.take();
        }
        catch (InterruptedException e) {
            logger.warn("[getNext] Current thread was interrupted! %s", e.toString());
        }

        return InputKey.UNKNOWN;
    }

    public InputKey getNextAsync() {
        InputKey key = inputs.poll();
        if (key == null) {
            return InputKey.UNKNOWN;
        }
        return key;
    }

    public void clear() {
        inputs.clear();
    }

    /**
     * This keyPressed and keyReleased mechanism allows to add keys into the list only one typed key at a time.
     * @param input input key to be processed
     */
    @Override
    public void onKeyPressed(InputKey input) {
        if (!isKeyReleased) {
            return;
        }

        isKeyReleased = false;
        if (input == InputKey.UNKNOWN) {
            return;
        }

        try {
            inputs.put(input);
        }
        catch (InterruptedException ex) {
            logger.warn("Failed to add input to queue. Ex.: %s", ex);
        }
    }

    @Override
    public void onKeyReleased(InputKey input) {
        isKeyReleased = true;
    }

    @Override
    public void onKeyTyped(InputKey input) {
        // Skip.
    }
}
