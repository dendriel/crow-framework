package com.vrozsa.crowframework.input;

import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.api.input.KeysListener;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Keep tracks of pressed keys.
 * <p>
 *     WARNING: Must be attached to a screen component.
 * </p>
 */
final class TrackingInputHandler implements InputHandler, KeysListener {
    private final ReentrantLock keysLock;
    private final EnumMap<InputKey, Boolean> keys;
    private final Set<InputKey> pressedKeys;

    private TrackingInputHandler() {
        keysLock = new ReentrantLock();
        keys = new EnumMap<>(InputKey.class);
        pressedKeys = new HashSet<>();
    }

    public static TrackingInputHandler create() {
        return new TrackingInputHandler();
    }

    public boolean isKeyPressed(InputKey key) {
        keysLock.lock();
        try {
            return keys.getOrDefault(key, false);
        }
        finally {
            keysLock.unlock();
        }
    }

    public List<InputKey> getPressedKeys() {
        keysLock.lock();
        try {
            return new ArrayList<>(pressedKeys);
        }
        finally {
            keysLock.unlock();
        }
    }

    @Override
    public void readUntil(InputKey... keys) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onKeyPressed(InputKey input) {
        if (input == InputKey.UNKNOWN) {
            return;
        }

        keysLock.lock();
        try {
            keys.put(input, true);
            pressedKeys.add(input);
        }
        finally {
            keysLock.unlock();
        }
    }

    @Override
    public void onKeyReleased(InputKey input) {
        if (input == InputKey.UNKNOWN) {
            return;
        }

        keysLock.lock();
        try {
            keys.put(input, false);
            pressedKeys.remove(input);
        }
        finally {
            keysLock.unlock();
        }
    }

    @Override
    public void onKeyTyped(InputKey input) {
        // Skip.
    }

    @Override
    public InputKey getNext() {
        // not supported
        return InputKey.UNKNOWN;
    }

    @Override
    public void clear() {
        // not supported
    }
}
