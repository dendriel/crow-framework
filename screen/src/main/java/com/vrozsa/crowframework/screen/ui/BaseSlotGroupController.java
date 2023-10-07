package com.vrozsa.crowframework.screen.ui;

import com.vrozsa.crowframework.shared.api.game.GameCommand;
import com.vrozsa.crowframework.shared.api.game.BaseComponent;
import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.shared.api.input.InputKey;
import com.vrozsa.crowframework.screen.ui.api.ISlotGroupControllerCommand;

import java.util.HashMap;
import java.util.Map;


public abstract class BaseSlotGroupController implements BaseComponent {
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
        input.clear();
        isInterrupt = false;
        showView();

        boolean keepGoing;
        do {
            keepGoing = handleSlotGroupInteraction();
        } while (keepGoing);

        hideView();
        input.clear();
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
