package com.vrozsa.crowframework.screen.ui;

import com.vrozsa.crowframework.shared.api.game.GameCommand;
import com.vrozsa.crowframework.shared.api.input.InputHandler;
import com.vrozsa.crowframework.screen.ui.api.UISelectOption;
import com.vrozsa.crowframework.shared.api.audio.AudioClipPlayer;

public class SelectController extends AbstractSlotGroupController implements UISelect.SelectOptionListener {
    private final UISelect select;
    private final AudioClipPlayer audioClipPlayer;
    private final String moveClip;
    private final String selectClip;
    private GameCommand closeCommand;

    private boolean isInterrupted;

    private int selectedOptionIndex;

    public SelectController(InputHandler input, UISelect select) {
        this(input, select, new NullAudioClipPlayer(), null, null);
    }

    public SelectController(InputHandler input, UISelect select, AudioClipPlayer audioClipPlayer, String moveClip, String selectClip) {
        super(input);
        this.select = select;
        this.audioClipPlayer = audioClipPlayer;
        this.moveClip = moveClip;
        this.selectClip = selectClip;
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
        audioClipPlayer.play(moveClip);
    }

    private void moveHandlerUp() {
        select.moveHandlerUp();
        audioClipPlayer.play(moveClip);
    }

    private void select() {
        select.select();
        audioClipPlayer.play(selectClip);
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
