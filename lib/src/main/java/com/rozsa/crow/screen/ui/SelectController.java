package com.rozsa.crow.screen.ui;

import com.rozsa.crow.game.GameCommand;
import com.rozsa.crow.input.InputHandler;
import com.rozsa.crow.screen.ui.api.UISelectOption;
import com.rozsa.crow.sound.NullSfxPlayer;
import com.rozsa.crow.sound.api.SfxPlayer;

public class SelectController extends BaseSlotGroupController implements UISelect.SelectOptionListener {
    private final UISelect select;
    private final SfxPlayer sfxPlayer;
    private final String moveSfx;
    private final String selectSfx;
    private GameCommand closeCommand;

    private boolean isInterrupted;

    private int selectedOptionIndex;

    public SelectController(InputHandler input, UISelect select) {
        this(input, select, new NullSfxPlayer(), null, null);
    }

    public SelectController(InputHandler input, UISelect select, SfxPlayer sfxPlayer, String moveSfx, String selectSfx) {
        super(input);
        this.select = select;
        this.sfxPlayer = sfxPlayer;
        this.moveSfx = moveSfx;
        this.selectSfx = selectSfx;
        select.registerListener(this);

        closeCommand = null;
    }

    @Override
    protected void mapInputToControllerCommands() {
        inputToCommandMapper.put(GameCommand.MOVE_DOWN, this::moveHandlerDown);
        inputToCommandMapper.put(GameCommand.MOVE_UP, this::moveHandlerUp);
        inputToCommandMapper.put(GameCommand.ACTION, this::select);
    }

    public void setCloseCommand(GameCommand closeCommand) {
        this.closeCommand = closeCommand;
    }

    private void moveHandlerDown() {
        select.moveHandlerDown();
        sfxPlayer.play(moveSfx);
    }

    private void moveHandlerUp() {
        select.moveHandlerUp();
        sfxPlayer.play(moveSfx);
    }

    private void select() {
        select.select();
        sfxPlayer.play(selectSfx);
    }

    public UISelectOption getOption() {
        isInterrupted = false;
        selectedOptionIndex = -1;
        super.handle();

        if (selectedOptionIndex == -1) {
            // Interrupted.
            return null;
        }
        return select.getOption(selectedOptionIndex);
    }

    @Override
    public void onOptionSelected(int optionIndex) {
        selectedOptionIndex = optionIndex;
        isInterrupted = true;
    }

    @Override
    protected boolean isCloseCommand(GameCommand command) {
        return command.equals(closeCommand);
    }

    @Override
    protected boolean interrupt() {
        return isInterrupted;
    }

    @Override
    protected void showView() {

    }

    @Override
    protected void hideView() {

    }
}
