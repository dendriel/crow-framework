package com.vrozsa.crowframework.sample.engine;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.GameObjectBuilder;
import com.vrozsa.crowframework.engine.InputManager;
import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.game.component.Identifier;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Vector;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import static com.vrozsa.crowframework.sample.TestValues.BACKGROUND_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.BANDIT_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.CHARS_SPRITE_SIZE;
import static com.vrozsa.crowframework.sample.TestValues.HERO_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.SCREEN_HEIGHT;
import static com.vrozsa.crowframework.sample.TestValues.SCREEN_WIDTH;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_LEFT;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_RIGHT;

public class CollidingGameObject {
    private static final LoggerService logger = LoggerService.of(CollidingGameObject.class);

    public static void main(String[] args) {
        final int screenMiddleX = SCREEN_WIDTH / 2;
        final int screenMiddleY = SCREEN_HEIGHT / 2;

        var crow = CrowEngine.create(SCREEN_WIDTH, SCREEN_HEIGHT, Color.gray());

        var gameManager = crow.getGameManager();

        gameManager.addGameObject(GameObjectBuilder.atOrigin()
                .addStaticRenderer(0,BACKGROUND_IMAGE_FILE, SCREEN_WIDTH, SCREEN_HEIGHT)
                .build());

        GameObject heroGO = GameObjectBuilder.of(Vector.of(screenMiddleX-40, screenMiddleY-40, 0))
                .addStaticRenderer(HERO_IMAGE_FILE, CHARS_SPRITE_SIZE.getWidth(), CHARS_SPRITE_SIZE.getHeight())
                .addSquareCollider(1000)
                .addIdentifier("Hero", 1)
                .addCollisionHandler((source, target) -> {
                    var sourceID = source.getComponent(Identifier.class);
                    var targetID = target.getComponent(Identifier.class);
                    logger.debug("GO {0} has collided with {1}", sourceID.getName(), targetID.getName());
                })
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

        GameObject banditGO = GameObjectBuilder.of(Vector.of(screenMiddleX-240, screenMiddleY-40, 0))
                .addStaticRenderer(BANDIT_IMAGE_FILE, CHARS_SPRITE_SIZE.getWidth(), CHARS_SPRITE_SIZE.getHeight())
                .addSquareCollider()
                .addIdentifier("Bandit", 2)
                .build();

        gameManager.addGameObject(banditGO);
    }
}
