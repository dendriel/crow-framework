package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.shared.api.input.InputKey;

/**
 * Manages input related features.
 */
public interface InputManager {
    /**
     * Blocks until some of the expected keys are typed.
     * @param keys target keys to be read.
     */
    void readUntil(final InputKey...keys);
}
