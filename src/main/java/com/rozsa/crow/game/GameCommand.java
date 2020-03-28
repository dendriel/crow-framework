package com.rozsa.crow.game;

import com.rozsa.crow.input.InputKey;

import java.util.HashMap;
import java.util.Map;

public enum GameCommand {
    MOVE_LEFT(InputKey.LEFT),
    MOVE_UP(InputKey.UP),
    MOVE_RIGHT(InputKey.RIGHT),
    MOVE_DOWN(InputKey.DOWN),
    PICKUP(InputKey.ENTER),
    REMOVE(InputKey.BACKSPACE),
    INVENTORY(InputKey.I),
    CLOSE(InputKey.ESCAPE),
    SWITCH_TARGET(InputKey.TAB),
    ACTION(InputKey.SPACE),
    BUY(InputKey.B),
    SELL(InputKey.S),
    ATTRIBUTES(InputKey.C),
    USE_ITEM_00(InputKey.KEY_00),
    USE_ITEM_01(InputKey.KEY_01),
    USE_ITEM_02(InputKey.KEY_02),
    USE_ITEM_03(InputKey.KEY_03),
    USE_ITEM_04(InputKey.KEY_04),
    USE_ITEM_05(InputKey.KEY_05),
    USE_ITEM_06(InputKey.KEY_06),
    USE_ITEM_07(InputKey.KEY_07),
    USE_ITEM_08(InputKey.KEY_08),
    USE_ITEM_09(InputKey.KEY_09),
    SAVE(InputKey.INSERT),
    LOAD(InputKey.HOME),

    // DEBUG ONLY
    FLY_UP(InputKey.NUMPAD_UP),
    FLY_RIGHT(InputKey.NUMPAD_RIGHT),
    FLY_DOWN(InputKey.NUMPAD_DOWN),
    FLY_LEFT(InputKey.NUMPAD_LEFT),

    ;

    private static Map<InputKey, GameCommand> inputToCommandMapper;
    static {
        inputToCommandMapper = new HashMap<>();
        inputToCommandMapper.put(MOVE_LEFT.getInput(), MOVE_LEFT);
        inputToCommandMapper.put(MOVE_UP.getInput(), MOVE_UP);
        inputToCommandMapper.put(MOVE_RIGHT.getInput(), MOVE_RIGHT);
        inputToCommandMapper.put(MOVE_DOWN.getInput(), MOVE_DOWN);
        inputToCommandMapper.put(PICKUP.getInput(), PICKUP);
        inputToCommandMapper.put(INVENTORY.getInput(), INVENTORY);
        inputToCommandMapper.put(CLOSE.getInput(), CLOSE);
        inputToCommandMapper.put(REMOVE.getInput(), REMOVE);
        inputToCommandMapper.put(SWITCH_TARGET.getInput(), SWITCH_TARGET);
        inputToCommandMapper.put(ACTION.getInput(), ACTION);
        inputToCommandMapper.put(BUY.getInput(), BUY);
        inputToCommandMapper.put(SELL.getInput(), SELL);
        inputToCommandMapper.put(ATTRIBUTES.getInput(), ATTRIBUTES);
        inputToCommandMapper.put(USE_ITEM_00.getInput(), USE_ITEM_00);
        inputToCommandMapper.put(USE_ITEM_01.getInput(), USE_ITEM_01);
        inputToCommandMapper.put(USE_ITEM_02.getInput(), USE_ITEM_02);
        inputToCommandMapper.put(USE_ITEM_03.getInput(), USE_ITEM_03);
        inputToCommandMapper.put(USE_ITEM_04.getInput(), USE_ITEM_04);
        inputToCommandMapper.put(USE_ITEM_05.getInput(), USE_ITEM_05);
        inputToCommandMapper.put(USE_ITEM_06.getInput(), USE_ITEM_06);
        inputToCommandMapper.put(USE_ITEM_07.getInput(), USE_ITEM_07);
        inputToCommandMapper.put(USE_ITEM_08.getInput(), USE_ITEM_08);
        inputToCommandMapper.put(USE_ITEM_09.getInput(), USE_ITEM_09);
        inputToCommandMapper.put(SAVE.getInput(), SAVE);
        inputToCommandMapper.put(LOAD.getInput(), LOAD);

        // DEBUG ONLY
        inputToCommandMapper.put(FLY_UP.getInput(), FLY_UP);
        inputToCommandMapper.put(FLY_RIGHT.getInput(), FLY_RIGHT);
        inputToCommandMapper.put(FLY_DOWN.getInput(), FLY_DOWN);
        inputToCommandMapper.put(FLY_LEFT.getInput(), FLY_LEFT);
    }

    private final InputKey input;

    GameCommand(InputKey input) {
        this.input = input;
    }

    public static GameCommand from(InputKey input) {
        GameCommand command = inputToCommandMapper.get(input);
        assert command != null : String.format("There is no player command mapped to input: %s", command);

        return command;
    }

    public InputKey getInput() {
        return input;
    }

    public boolean isMovement() {
        return equals(MOVE_LEFT) || equals(MOVE_RIGHT) ||
                equals(MOVE_UP) || equals(MOVE_DOWN);
    }

    public boolean isUseHotkey() {
        return equals(USE_ITEM_00) || equals(USE_ITEM_01) || equals(USE_ITEM_02) ||
                equals(USE_ITEM_03) || equals(USE_ITEM_04) || equals(USE_ITEM_05) ||
                equals(USE_ITEM_06) || equals(USE_ITEM_07) || equals(USE_ITEM_08) ||
                equals(USE_ITEM_09);
    }

    public boolean isPickup() {
        return equals(PICKUP);
    }

    public boolean isAction() {
        return equals(ACTION);
    }

    public boolean isInventory() {
        return equals(INVENTORY);
    }

    public boolean isAttributes() {
        return equals(ATTRIBUTES);
    }

    public boolean isClose() {
        return equals(CLOSE);
    }

    public boolean isSwitchTarget() {
        return equals(SWITCH_TARGET);
    }

    public boolean isSkill() {
        return equals(SELL);
    }

    public boolean isSave() {
        return equals(SAVE);
    }

    public boolean isLoad() {
        return equals(LOAD);
    }

    @Override
    public String toString() {
        return String.format("Input: " + input.toString());
    }
}
