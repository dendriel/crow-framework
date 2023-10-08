package com.vrozsa.crowframework.engine;


import com.vrozsa.crowframework.BufferedInputHandler;
import com.vrozsa.crowframework.TrackingInputHandler;
import com.vrozsa.crowframework.shared.api.game.GameCommand;
import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.shared.api.input.InputKey;

class CrowInputManager implements InputManager {
    private final InputHandler inputHandler;

    CrowInputManager() {
        this.inputHandler = new TrackingInputHandler();
    }

    InputHandler getInputHandler() {
        return inputHandler;
    }

    public GameCommand getCommand() {
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
