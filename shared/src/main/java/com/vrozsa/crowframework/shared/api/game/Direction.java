package com.vrozsa.crowframework.shared.api.game;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerates possible game directions.
 */
public enum Direction {
    NONE(-1, -1),
    UP (0, 2),
    RIGHT (1, 3),
    DOWN (2, 0),
    LEFT (3, 1),
    DOWN_LEFT(4, 7),
    DOWN_RIGHT(5, 6),
    UP_LEFT(6, 5),
    UP_RIGHT(7, 4),
    ;

    private byte id;
    private byte inverseDirection;

    Direction(int id, int inverseId) {
        this.id = (byte)id;
        inverseDirection =(byte)inverseId;
    }

    public byte getId() {
        return id;
    }

    public static Direction getInverseDirection(Direction direction) {
        return from(direction.inverseDirection);
    }

    public boolean isAxisDirection() {
        Direction direction = from(id);
        return direction == UP || direction == DOWN ||
                direction == LEFT || direction == RIGHT;
    }

    private static final Map<Byte, Direction> idToDirectionMapper;

    static {
        idToDirectionMapper = new HashMap<>();
        idToDirectionMapper.put(NONE.getId(), NONE);
        idToDirectionMapper.put(UP.getId(), UP);
        idToDirectionMapper.put(RIGHT.getId(), RIGHT);
        idToDirectionMapper.put(DOWN.getId(), DOWN);
        idToDirectionMapper.put(LEFT.getId(), LEFT);

        idToDirectionMapper.put(DOWN_LEFT.getId(), DOWN_LEFT);
        idToDirectionMapper.put(DOWN_RIGHT.getId(), DOWN_RIGHT);
        idToDirectionMapper.put(UP_LEFT.getId(), UP_LEFT);
        idToDirectionMapper.put(UP_RIGHT.getId(), UP_RIGHT);
    }

    public static Direction from(byte id) {
        return idToDirectionMapper.getOrDefault(id, NONE);
    }

    public static Direction from(GameCommand command) {
        if (command.equals(GameCommand.MOVE_LEFT) || command.equals(GameCommand.A)) {
            return LEFT;
        }
        if (command.equals(GameCommand.MOVE_RIGHT) || command.equals(GameCommand.D)) {
            return RIGHT;
        }
        if (command.equals(GameCommand.MOVE_UP) || command.equals(GameCommand.W)) {
            return UP;
        }
        if (command.equals(GameCommand.MOVE_DOWN) || command.equals(GameCommand.S)) {
            return DOWN;
        }

        if (command.equals(GameCommand.KEYPAD_01)) {
            return DOWN_LEFT;
        }
        if (command.equals(GameCommand.KEYPAD_03)) {
            return DOWN_RIGHT;
        }
        if (command.equals(GameCommand.KEYPAD_07)) {
            return UP_LEFT;
        }
        if (command.equals(GameCommand.KEYPAD_09)) {
            return UP_RIGHT;
        }

        assert false : String.format("Command can't be translated to a direction: %s", command);
        return null;
    }

    public static int getNewPosX(Direction direction, int currPosX) {
        if (direction == Direction.LEFT ||
            direction == Direction.DOWN_LEFT ||
            direction == Direction.UP_LEFT) {
            return currPosX - 80;
        }

        if (direction == Direction.RIGHT ||
            direction == Direction.DOWN_RIGHT ||
            direction == Direction.UP_RIGHT) {
            return currPosX + 80;
        }

        return currPosX;
    }

    public static int getNewPosY(Direction direction, int currPosY) {
        if (direction == Direction.UP ||
            direction == Direction.UP_LEFT |
            direction == Direction.UP_RIGHT) {
            return currPosY - 80;
        }

        if (direction == Direction.DOWN ||
            direction == Direction.DOWN_LEFT ||
            direction == Direction.DOWN_RIGHT) {
            return currPosY + 80;
        }

        return currPosY;
    }

//    public static Direction getDirection(int from, int to) {
//        if (from > to) {
//            return LEFT;
//        }
//        else {
//            return RIGHT;
//        }
//    }

    public static Direction fromOffset(final int x, int y) {
        var hor = NONE;
        var ver = NONE;

        if (x > 0) {
            hor = RIGHT;
        }
        else if (x < 0) {
            hor = LEFT;
        }

        if (y > 0) {
            ver = UP;
        }
        else if (y < 0) {
            ver = DOWN;
        }

        if (ver == UP) {
            if (hor == RIGHT) {
                return UP_RIGHT;
            }
            else if (hor == LEFT) {
                return UP_LEFT;
            }
            return UP;
        }

        if (ver == DOWN) {
            if (hor == RIGHT) {
                return DOWN_RIGHT;
            }
            else if (hor == LEFT) {
                return DOWN_LEFT;
            }
            return DOWN;
        }

        // RIGHT, LEFT or NONE.
        return hor;
    }
}
