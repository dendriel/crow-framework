package com.rozsa.crow.screen.ui;

import com.rozsa.crow.game.GameCommand;
import com.rozsa.crow.game.component.BaseComponent;
import com.rozsa.crow.input.InputHandler;
import com.rozsa.crow.input.InputKey;
import com.rozsa.crow.screen.ui.api.ISlotGroupControllerCommand;

import java.util.HashMap;
import java.util.Map;


public abstract class BaseSlotGroupController extends BaseComponent {
    protected final Map<GameCommand, ISlotGroupControllerCommand> inputToCommandMapper;
    private final InputHandler input;
    protected GameCommand lastCommand;
    protected boolean isInterrupt;

    public BaseSlotGroupController(InputHandler input) {
        this.input = input;
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
        input.clearCache();
        isInterrupt = false;
        showView();

        boolean keepGoing;
        do {
            keepGoing = handleSlotGroupInteraction();
        } while (keepGoing);

        hideView();
        input.clearCache();
    }

    private boolean handleSlotGroupInteraction() {
        if (interrupt()) {
            return false;
        }

        InputKey inputKey = input.getNext();
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
