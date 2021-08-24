package com.rozsa.crow.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

// Useful for checking key status.
public class InputHandlerKeyPressedMapper implements KeyListener {
    private final ReentrantLock keysLock;
    private final HashMap<InputKey, Boolean> keys;
    private final Set<InputKey> pressedKeys;

    public InputHandlerKeyPressedMapper() {
        keysLock = new ReentrantLock();
        keys = new HashMap<>();
        pressedKeys = new HashSet<>();
    }

    public boolean isKeyPressed(InputKey key) {
        keysLock.lock();
        try {
            return keys.getOrDefault(key, false);
        } finally {
            keysLock.unlock();
        }
    }

    public List<InputKey> getPressedKeys() {
        keysLock.lock();
        try {
            return new ArrayList<>(pressedKeys);
        } finally {
            keysLock.unlock();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        InputKey input = InputKey.from(keyCode);
        if (input == InputKey.UNKNOWN) {
            System.out.println("Unmapped Keycode pressed: " + keyCode);
            return;
        }

        keysLock.lock();
        try {
            keys.put(input, true);
            pressedKeys.add(input);
        } finally {
            keysLock.unlock();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        InputKey input = InputKey.from(keyCode);
        if (input == InputKey.UNKNOWN) {
            System.out.println("Unmapped Keycode released: " + keyCode);
            return;
        }

        keysLock.lock();
        try {
            keys.put(input, false);
            pressedKeys.remove(input);
        } finally {
            keysLock.unlock();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
