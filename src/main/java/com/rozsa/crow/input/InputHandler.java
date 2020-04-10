package com.rozsa.crow.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.LinkedBlockingQueue;

public class InputHandler implements KeyListener {
    private boolean isKeyReleased = true;

    private volatile LinkedBlockingQueue<InputKey> inputs;

    public InputHandler() {
        inputs = new LinkedBlockingQueue<>(5);
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
        System.out.println("Keycode: " + keyCode);
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
