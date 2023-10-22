package com.vrozsa.crowframework.shared.api.input;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps available input keys names.
 */
public enum InputKey {
    UNKNOWN(-1),
    BACKSPACE(8),
    TAB(9),
    ENTER(10),
    ESCAPE(27),
    SPACE(32),
    HOME(36),
    LEFT(37),
    UP(38),
    RIGHT(39),
    DOWN(40),
    KEY_00(48),
    KEY_01(49),
    KEY_02(50),
    KEY_03(51),
    KEY_04(52),
    KEY_05(53),
    KEY_06(54),
    KEY_07(55),
    KEY_08(56),
    KEY_09(57),
    A(65),
    B(66),
    C(67),
    D(68),
    I(73),
    S(83),
    W(87),
    NUMPAD_DOWN(98),
    NUMPAD_LEFT(100),
    NUMPAD_RIGHT(102),
    NUMPAD_UP(104),
    INSERT(155),

    KEYPAD_01(97),
    KEYPAD_03(99),
    KEYPAD_07(103),
    KEYPAD_09(105),
    ;

    private static final Map<Integer, InputKey> keyCodeToPlayerInputMapper;
    static {
        keyCodeToPlayerInputMapper = new HashMap<>();
        keyCodeToPlayerInputMapper.put(UNKNOWN.getKeyCode(), UNKNOWN);
        keyCodeToPlayerInputMapper.put(BACKSPACE.getKeyCode(), BACKSPACE);
        keyCodeToPlayerInputMapper.put(TAB.getKeyCode(), TAB);
        keyCodeToPlayerInputMapper.put(ENTER.getKeyCode(), ENTER);
        keyCodeToPlayerInputMapper.put(ESCAPE.getKeyCode(), ESCAPE);
        keyCodeToPlayerInputMapper.put(SPACE.getKeyCode(), SPACE);
        keyCodeToPlayerInputMapper.put(HOME.getKeyCode(), HOME);
        keyCodeToPlayerInputMapper.put(LEFT.getKeyCode(), LEFT);
        keyCodeToPlayerInputMapper.put(UP.getKeyCode(), UP);
        keyCodeToPlayerInputMapper.put(RIGHT.getKeyCode(), RIGHT);
        keyCodeToPlayerInputMapper.put(DOWN.getKeyCode(), DOWN);
        keyCodeToPlayerInputMapper.put(KEY_00.getKeyCode(), KEY_00);
        keyCodeToPlayerInputMapper.put(KEY_01.getKeyCode(), KEY_01);
        keyCodeToPlayerInputMapper.put(KEY_02.getKeyCode(), KEY_02);
        keyCodeToPlayerInputMapper.put(KEY_03.getKeyCode(), KEY_03);
        keyCodeToPlayerInputMapper.put(KEY_04.getKeyCode(), KEY_04);
        keyCodeToPlayerInputMapper.put(KEY_05.getKeyCode(), KEY_05);
        keyCodeToPlayerInputMapper.put(KEY_06.getKeyCode(), KEY_06);
        keyCodeToPlayerInputMapper.put(KEY_07.getKeyCode(), KEY_07);
        keyCodeToPlayerInputMapper.put(KEY_08.getKeyCode(), KEY_08);
        keyCodeToPlayerInputMapper.put(KEY_09.getKeyCode(), KEY_09);
        keyCodeToPlayerInputMapper.put(A.getKeyCode(), A);
        keyCodeToPlayerInputMapper.put(B.getKeyCode(), B);
        keyCodeToPlayerInputMapper.put(C.getKeyCode(), C);
        keyCodeToPlayerInputMapper.put(D.getKeyCode(), D);
        keyCodeToPlayerInputMapper.put(I.getKeyCode(), I);
        keyCodeToPlayerInputMapper.put(S.getKeyCode(), S);
        keyCodeToPlayerInputMapper.put(W.getKeyCode(), W);
        keyCodeToPlayerInputMapper.put(NUMPAD_DOWN.getKeyCode(), NUMPAD_DOWN);
        keyCodeToPlayerInputMapper.put(NUMPAD_LEFT.getKeyCode(), NUMPAD_LEFT);
        keyCodeToPlayerInputMapper.put(NUMPAD_RIGHT.getKeyCode(), NUMPAD_RIGHT);
        keyCodeToPlayerInputMapper.put(NUMPAD_UP.getKeyCode(), NUMPAD_UP);
        keyCodeToPlayerInputMapper.put(INSERT.getKeyCode(), INSERT);

        keyCodeToPlayerInputMapper.put(KEYPAD_01.getKeyCode(), KEYPAD_01);
        keyCodeToPlayerInputMapper.put(KEYPAD_03.getKeyCode(), KEYPAD_03);
        keyCodeToPlayerInputMapper.put(KEYPAD_07.getKeyCode(), KEYPAD_07);
        keyCodeToPlayerInputMapper.put(KEYPAD_09.getKeyCode(), KEYPAD_09);

    }
    private final int keyCode;

    InputKey(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * Maps a InputKey from its numeric representation.
     * @param keyCode numeric InputKey representation.
     * @return the mapped InputKey; UNKNOWN if the input value doesn't map to an input.
     */
    public static InputKey from(int keyCode) {
        InputKey input = keyCodeToPlayerInputMapper.get(keyCode);
        if (input == null) {
            return InputKey.UNKNOWN;
        }

        return input;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
