package com.vrozsa.crowframework;

import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Reads and buffers typed keys until read.
 * This kind of input handler may be useful if we want to track all player commands and reproduce it sequentially one
 * action after another (so the player types the commands and sees the result progressively).
 * WARNING: Must be attached to a screen component.
 */
public class BufferedInputHandler implements InputHandler {
    private static final LoggerService logger = LoggerService.of(BufferedInputHandler.class);
    private final LinkedBlockingQueue<InputKey> inputs;
    private boolean isKeyReleased = true;

    public BufferedInputHandler() {
        inputs = new LinkedBlockingQueue<>(5);
    }

    public BufferedInputHandler(int inputsBufferSize) {
        inputs = new LinkedBlockingQueue<>(inputsBufferSize);
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
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (!isKeyReleased) {
            return;
        }

        isKeyReleased = false;

        int keyCode = e.getKeyCode();
//        System.out.println("Keycode: " + keyCode);
        var input = InputKey.from(keyCode);
        if (input == InputKey.UNKNOWN) {
            logger.warn("Unmapped Keycode: %s", keyCode);
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
    public void keyReleased(KeyEvent e) {
        isKeyReleased = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
