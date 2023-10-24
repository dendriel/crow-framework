package com.vrozsa.crowframework.engine;


import com.vrozsa.crowframework.input.InputHandler;
import com.vrozsa.crowframework.input.PointerHandler;
import com.vrozsa.crowframework.shared.api.game.GameCommand;
import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.shared.api.screen.OffsetGetter;

import java.util.List;

class CrowInputManager implements InputManager {
    private final InputHandler inputHandler;
    private final PointerHandler pointerHandler;

    CrowInputManager(OffsetGetter screenOffsetGetter) {
        this.inputHandler = InputHandler.createTracking();
        this.pointerHandler = PointerHandler.create(screenOffsetGetter);
    }

    InputHandler getInputHandler() {
        return inputHandler;
    }

    @Override
    public PointerHandler getPointerHandler() {
        return pointerHandler;
    }

    @Override
    public GameCommand getCommand() {
        var pressedKeys = inputHandler.getPressedKeys();
        if (pressedKeys.isEmpty()) {
            return GameCommand.UNKNOWN;
        }
        return GameCommand.from(pressedKeys.get(0));
    }

    @Override
    public List<GameCommand> getAllCommands() {
        var pressedKeys = inputHandler.getPressedKeys();
        if (pressedKeys.isEmpty()) {
            return List.of();
        }
        return pressedKeys.stream()
                .map(GameCommand::from)
                .toList();
    }

    @Override
    public void readUntil(final InputKey...keys) {
        inputHandler.readUntil(keys);
    }
}
