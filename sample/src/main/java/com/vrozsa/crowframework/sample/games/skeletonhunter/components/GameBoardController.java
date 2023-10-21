package com.vrozsa.crowframework.sample.games.skeletonhunter.components;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Offsetable;

/**
 * Controls the game boards. As the player moves forward in the map, it will move the board that is invisible ahead
 * of the current board being displayed. This way, the player will always see a board while moving forward. This gives
 * an impression of an 'infinite' map, but we are just moving the 2 boards game objects to achieve this effect.
 */
public class GameBoardController extends AbstractComponent {

    private final GameObject boardA;
    private final GameObject boardB;
    private final int boardWidth;
    private final int screenWidth;
    private final Offsetable camera;

    /**
     * Creates a new game board controller.
     * @param visibleBoard game object to be used in the swapping (this board starts visible).
     * @param hiddenBoard game object to be used in the swapping (this board starts hidden).
     * @param boardWidth width of the boards to calculate when the swap occurs.
     * @param camera camera offset getter, so we know what part of the board is being displayed.
     */
    public GameBoardController(GameObject visibleBoard, GameObject hiddenBoard, int boardWidth, int screenWidth, Offsetable camera) {
        this.boardA = visibleBoard;
        this.boardB = hiddenBoard;
        this.boardWidth = boardWidth;
        this.screenWidth = screenWidth;
        this.camera = camera;
    }

    @Override
    public void lateUpdate() {
        super.lateUpdate();

        var camOffset = camera.getOffset();

        // make the module of offsetX by boardWidth, so we always have the relative position of camera by the board;
        // also, adds the half screen offset, so we use the center of the screen instead of the left corner.
        var relativeX = (camOffset.getX() % boardWidth) + (screenWidth / 2);

        // is the current center of the screen to the left or to the right of the game board?
        if (relativeX >= boardWidth / 2) {
            moveBoardToNextPosition();
        }
    }

    private void moveBoardToNextPosition() {
        var camOffset = camera.getOffset();
        int nextX = ((camOffset.getX() / boardWidth) + 1) * boardWidth;

        // Check if we have already set a board in the next position, otherwise we will keep moving the boards.
        if (boardA.getPosition().getX() == nextX || boardB.getPosition().getX() == nextX) {
            return;
        }

        getInvisibleBoard()
                .getPosition()
                .setPosition(nextX, 0);
    }

    private GameObject getInvisibleBoard() {
        // Get the leftmost board.
        if (boardA.getPosition().getX() < boardB.getPosition().getX()) {
            return boardA;
        }

        return boardB;
    }
}
