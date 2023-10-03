package com.vrozsa.crowframework;

import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.shared.api.input.InputKey;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Keep tracks of pressed keys.
 * WARNING: Must be attached to a screen component.
 */
public class TrackingInputHandler implements InputHandler {
    private final ReentrantLock keysLock;
    private final HashMap<InputKey, Boolean> keys;
    private final Set<InputKey> pressedKeys;

    public TrackingInputHandler() {
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

    @Override
    public InputKey getNext() {
        // not supported
        return InputKey.UNKNOWN;
    }

    @Override
    public void clearCache() {
        // not supported
    }
}
