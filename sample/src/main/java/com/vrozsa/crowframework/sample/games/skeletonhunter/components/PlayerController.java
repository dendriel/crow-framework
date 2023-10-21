package com.vrozsa.crowframework.sample.games.skeletonhunter.components;

import com.vrozsa.crowframework.engine.InputManager;
import com.vrozsa.crowframework.game.component.AbstractComponent;

import static com.vrozsa.crowframework.shared.api.game.GameCommand.ACTION;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_DOWN;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_LEFT;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_RIGHT;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_UP;

/**
 * The player controller allows the player to issue commands.
 */
public class PlayerController extends AbstractComponent {
    private CharacterDriver driver;

    private final InputManager inputManager;

    public PlayerController(final InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public void wrapUp() {
        super.wrapUp();

        driver = getComponent(CharacterDriver.class);
        assert driver != null : "PlayerController requires a CharacterDriver!";
    }

    @Override
    public void update() {
        super.update();

        var commands = inputManager.getAllCommands();

        var diagonalMovement = (commands.contains(MOVE_UP) || commands.contains(MOVE_DOWN)) &&
                (commands.contains(MOVE_LEFT) || commands.contains(MOVE_RIGHT));

        if (commands.contains(MOVE_UP)) {
            driver.moveUp(diagonalMovement);
        }
        else if (commands.contains(MOVE_DOWN)) {
            driver.moveDown(diagonalMovement);
        }

        if (commands.contains(MOVE_LEFT)) {
            driver.moveLeft(diagonalMovement);
        }
        else if (commands.contains(MOVE_RIGHT)) {
            driver.moveRight(diagonalMovement);
        }

        if (commands.contains(ACTION)) {
            driver.attack();
        }


//        boolean shoot = commands.contains(ACTION);
//
//        pos.addOffset(Offset.of(offsetX, offsetY));
//        var renderer = getComponent(Renderer.class);
//
//        if (commands.contains(MOVE_LEFT) && facingRight) {
//            facingRight = false;
//            renderer.setFlipX(true);
//        }
//        else if (commands.contains(MOVE_RIGHT) && !facingRight) {
//            facingRight = true;
//            renderer.setFlipX(false);
//        }
    }
}
