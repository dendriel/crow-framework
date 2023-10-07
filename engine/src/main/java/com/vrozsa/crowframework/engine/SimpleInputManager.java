package com.vrozsa.crowframework.engine;


import com.vrozsa.crowframework.BufferedInputHandler;
import com.vrozsa.crowframework.shared.api.game.GameCommand;
import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.shared.api.input.InputKey;

class SimpleInputManager implements InputManager {
    private final InputHandler inputHandler;

    SimpleInputManager() {
        this.inputHandler = new BufferedInputHandler();
    }

    InputHandler getInputHandler() {
        return inputHandler;
    }

    GameCommand getCommand() {
        var pressedKeys = inputHandler.getPressedKeys();
        if (pressedKeys.isEmpty()) {
            return GameCommand.UNKNOWN;
        }
        return GameCommand.from(pressedKeys.get(0));
    }

    @Override
    public void readUntil(final InputKey...keys) {
        inputHandler.readUntil(keys);
    }
}
