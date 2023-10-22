package com.vrozsa.crowframework.screen.ui;

import com.vrozsa.crowframework.shared.api.game.GameCommand;
import com.vrozsa.crowframework.shared.api.game.BaseComponent;
import com.vrozsa.crowframework.shared.api.input.KeysReader;
import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.screen.ui.api.ISlotGroupControllerCommand;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractSlotGroupController implements BaseComponent {
    protected final Map<GameCommand, ISlotGroupControllerCommand> inputToCommandMapper;
    private final KeysReader keysReader;
    protected GameCommand lastCommand;
    protected boolean isInterrupt;

    public AbstractSlotGroupController(KeysReader keysReader) {
        this.keysReader = keysReader;
        inputToCommandMapper = new HashMap<>();

        mapInputToControllerCommands();
    }

    public GameCommand getLastCommand() {
        return lastCommand;
    }

    protected abstract void mapInputToControllerCommands();

    protected abstract void showView();

    protected abstract void hideView();

    public void handle() {
        keysReader.clear();
        isInterrupt = false;
        showView();

        boolean keepGoing;
        do {
            keepGoing = handleSlotGroupInteraction();
        } while (keepGoing);

        hideView();
        keysReader.clear();
    }

    private boolean handleSlotGroupInteraction() {
        if (interrupt()) {
            return false;
        }

        InputKey inputKey = keysReader.getNext();
        lastCommand = GameCommand.from(inputKey);

        if (isCloseCommand(lastCommand)) {
            return false;
        }

        ISlotGroupControllerCommand command = inputToCommandMapper.get(lastCommand);
        if (command == null) {
            System.out.printf("Skip command unknown to this controller [%s].\n", lastCommand);
            return true;
        }

        command.invoke();

        return true;
    }

    protected boolean isCloseCommand(GameCommand command) {
        return command.isClose();
    }

    protected boolean interrupt() {
        return isInterrupt;
    }
}
