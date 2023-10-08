package com.vrozsa.crowframework.sample.engine;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.GameObjectBuilder;
import com.vrozsa.crowframework.engine.InputManager;
import com.vrozsa.crowframework.game.component.BaseComponent;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Vector;

import static com.vrozsa.crowframework.sample.TestValues.BACKGROUND_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.HERO_IMAGE_FILE;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_LEFT;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_RIGHT;

public class SimpleEngineSetup {

    public static void main(String[] args) {
        final int screenWidth = 800;
        final int screenHeight = 600;
        final int screenMiddleX = screenWidth / 2;
        final int screenMiddleY = screenHeight / 2;

        var crow = CrowEngine.create(screenWidth, screenHeight, Color.gray());

        var screenManager = crow.getScreenManager();
        screenManager.addIcon(BACKGROUND_IMAGE_FILE, 0, 0, screenWidth, screenHeight);

        var gameManager = crow.getGameManager();

        GameObject heroGO = new GameObjectBuilder(Vector.of(screenMiddleX-40, screenMiddleY-40, 0))
                .addStaticRenderer(HERO_IMAGE_FILE, 80, 80)
                .build();


        final int movingSpeed = 5;

        heroGO.addComponent(new BaseComponent() {
            private final InputManager inputManager = crow.getInputManager();

            private boolean facingRight = true;

            @Override
            public void update() {
                super.update();
                var command = inputManager.getCommand();
                var pos = heroGO.getPosition();
                int newX = pos.getX();
                int newY = pos.getY();

                switch (command) {
                    case MOVE_UP -> newY -= movingSpeed;
                    case MOVE_DOWN -> newY += movingSpeed;
                    case MOVE_LEFT -> newX -= movingSpeed;
                    case MOVE_RIGHT -> newX += movingSpeed;
                }

                pos.setPosition(newX, newY);
                var renderer = heroGO.getComponent(Renderer.class);

                if (command == MOVE_LEFT && facingRight) {
                    facingRight = false;
                    renderer.setFlipX(true);
                }
                else if (command == MOVE_RIGHT && !facingRight) {
                    facingRight = true;
                    renderer.setFlipX(false);
                }
            }
        });

        gameManager.addGameObject(heroGO);

//        GameCommand command;
//        do {
//            command = inputManager.getCommand();
//            var pos = heroGO.getPosition();
//            int newX = pos.getX();
//            int newY = pos.getY();
//
//            switch (command) {
//                case MOVE_UP -> newY -= movingSpeed;
//                case MOVE_DOWN -> newY += movingSpeed;
//                case MOVE_LEFT -> newX -= movingSpeed;
//                case MOVE_RIGHT -> newX += movingSpeed;
//            }
//
//            pos.setPosition(newX, newY);
//        } while (!command.isClose());
    }
}
