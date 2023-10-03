package com.vrozsa.crowframework;

import com.vrozsa.crowframework.shared.api.input.InputKey;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Reads and buffers typed keys until read.
 * WARNING: Must be attached to a screen component.
 */
public class BufferedInputHandler implements KeyListener {
    private final LinkedBlockingQueue<InputKey> inputs;
    private boolean isKeyReleased = true;

    public BufferedInputHandler() {
        inputs = new LinkedBlockingQueue<>(5);
    }

    public BufferedInputHandler(int inputsBufferSize) {
        inputs = new LinkedBlockingQueue<>(inputsBufferSize);
    }

    public void readUntil(InputKey input) {
        InputKey nextInput;
        do {
            try {
                nextInput = inputs.take();
            } catch (Exception e) {
                e.printStackTrace();
                nextInput = InputKey.UNKNOWN;
            }
        } while (!nextInput.equals(input));
    }

    public InputKey getNext() {
        try {
            return inputs.take();
        } catch (Exception e) {
            System.out.println("Failed to get input from queue!");
            e.printStackTrace();
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

    public void clearCache() {
        inputs.clear();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isKeyReleased) {
            return;
        }

        isKeyReleased = false;

        int keyCode = e.getKeyCode();
//        System.out.println("Keycode: " + keyCode);
        InputKey input = InputKey.from(keyCode);
        if (input == InputKey.UNKNOWN) {
            System.out.println("Unmapped Keycode: " + keyCode);
            return;
        }

        try {
            inputs.put(input);
        } catch (InterruptedException ex) {
            System.out.printf("Failed to add input to queue. Ex.: %s", ex);
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
