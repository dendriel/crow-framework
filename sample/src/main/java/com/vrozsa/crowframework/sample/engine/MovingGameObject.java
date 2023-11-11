package com.vrozsa.crowframework.sample.engine;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.GameObjectBuilder;
import com.vrozsa.crowframework.engine.InputManager;
import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Vector;

import static com.vrozsa.crowframework.sample.TestValues.*;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_LEFT;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_RIGHT;

public class MovingGameObject {

    public static void main(String[] args) {
        final int screenMiddleX = SCREEN_WIDTH / 2;
        final int screenMiddleY = SCREEN_HEIGHT / 2;

        var crow = CrowEngine.create(WINDOW_SIZE, Color.gray());

        var gameManager = crow.getGameManager();

        gameManager.addGameObject(GameObjectBuilder.atOrigin()
                .addStaticRenderer(0,BACKGROUND_IMAGE_FILE, SCREEN_WIDTH, SCREEN_HEIGHT)
                .build());

        GameObject heroGO = GameObjectBuilder.of(Vector.of(screenMiddleX-40, screenMiddleY-40, 0))
                .addStaticRenderer(HERO_IMAGE_FILE, 80, 80)
                .build();


        final int movingSpeed = 5;

        heroGO.addComponent(new AbstractComponent() {
            private final InputManager inputManager = crow.getInputManager();

            private boolean facingRight = true;

            @Override
            public void update() {
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
