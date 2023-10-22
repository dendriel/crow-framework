package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.shared.api.game.GameCommand;
import com.vrozsa.crowframework.shared.api.input.InputKey;

import java.util.List;

/**
 * Manages input related features.
 */
public interface InputManager {
    /**
     * Blocks until some of the expected keys are typed.
     * @param keys target keys to be read.
     */
    void readUntil(final InputKey...keys);


    /**
     * Get the current game command the user is inputting (keyboard keys are mapped to game commands).
     * @return the next game command if any.
     */
    GameCommand getCommand();

    /**
     * Get all commands corresponding to the currently pressed keys.
     * @return all commands available right now.
     */
    List<GameCommand> getAllCommands();
}
