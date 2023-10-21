package com.vrozsa.crowframework.sample.engine;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.CrowEngineConfig;
import com.vrozsa.crowframework.engine.GameObjectBuilder;
import com.vrozsa.crowframework.engine.InputManager;
import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.game.component.Identifier;
import com.vrozsa.crowframework.shared.api.game.Direction;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Renderer;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Vector;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import java.util.Set;

import static com.vrozsa.crowframework.sample.TestValues.ARROW_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.BACKGROUND_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.BANDIT_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.CHARS_SPRITE_SIZE;
import static com.vrozsa.crowframework.sample.TestValues.HERO_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.SCREEN_HEIGHT;
import static com.vrozsa.crowframework.sample.TestValues.SCREEN_WIDTH;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.ACTION;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_DOWN;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_LEFT;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_RIGHT;
import static com.vrozsa.crowframework.shared.api.game.GameCommand.MOVE_UP;

public class CollisionBlocking {
    private static final LoggerService logger = LoggerService.of(CollidingGameObject.class);

    public static void main(String[] args) {
        final int screenMiddleX = SCREEN_WIDTH / 2;
        final int screenMiddleY = SCREEN_HEIGHT / 2;

        var crowConfig = CrowEngineConfig.builder()
                .showGizmos(true)
                .screenWidth(SCREEN_WIDTH)
                .screenHeight(SCREEN_HEIGHT)
                .color(Color.gray())
                .build();

        var crow = CrowEngine.create(crowConfig);

        var gameManager = crow.getGameManager();

        gameManager.addGameObject(GameObjectBuilder.atOrigin()
                .addStaticRenderer(0,BACKGROUND_IMAGE_FILE, SCREEN_WIDTH, SCREEN_HEIGHT)
                .build());

        var arrowGO = GameObjectBuilder.of(Vector.origin())
                .setActive(false)
                .addStaticRenderer(ARROW_IMAGE_FILE, CHARS_SPRITE_SIZE.getWidth(), CHARS_SPRITE_SIZE.getHeight())
//                .addSquareCollider()
                .addSquareCollider(100, "hero_projectile", Set.of("enemies"), Rect.of(5, 30, 70, 20))
                .addCollisionGizmos()
                .addIdentifier("Arrow", 2)
                .addCollisionHandler((source, target) -> {
                    var targetID = target.getComponent(Identifier.class);
                    if (targetID.getName().equals("Bandit")) {
                        // disable the bandit because it was hit.
                        target.setActive(false);
                    }

                    if (targetID.getName().equals("Hero")) {
                        return;
                    }
                    // deactivate the arrowGO in any case because it has collided.
                    source.setActive(false);
                })
                // Arrow behavior.
                .addComponent(new ArrowController(10))
                .build();

        gameManager.addGameObject(arrowGO);

        final int movingSpeed = 5;
        final int diagonallyMovingSpeed = 3;

        var heroGO = GameObjectBuilder.of(Vector.of(screenMiddleX-40, screenMiddleY-40, 0))
                .addStaticRenderer(HERO_IMAGE_FILE, CHARS_SPRITE_SIZE.getWidth(), CHARS_SPRITE_SIZE.getHeight())
                .addSquareCollider(1000, "hero", 100, Set.of("enemies"), Rect.of(20, 5, 40, 70))
                .addCollisionGizmos()
                .addIdentifier("Hero", 1)
                .addCollisionHandler((source, target) -> {
                    var sourceID = source.getComponent(Identifier.class);
                    var targetID = target.getComponent(Identifier.class);
                    logger.debug("GO {0} has collided with {1}", sourceID.getName(), targetID.getName());
                })
                .addComponent(new AbstractComponent() {
                    private final InputManager inputManager = crow.getInputManager();
                    private boolean facingRight = true;

                    @Override
                    public void update() {
                        super.update();
                        var commands = inputManager.getAllCommands();
                        var pos = getPosition();
//                        int newX = pos.getX();
//                        int newY = pos.getY();
                        int offsetX = 0;
                        int offsetY = 0;

                        int speed = movingSpeed;

                        if ((commands.contains(MOVE_UP) || commands.contains(MOVE_DOWN)) &&
                                (commands.contains(MOVE_LEFT) || commands.contains(MOVE_RIGHT))) {
                            speed = diagonallyMovingSpeed;
                        }

                        // This way of reading the commands allows to handle multiple player actions per frame.
                        // This allows the character to move diagonally and shoot while moving.
                        if (commands.contains(MOVE_UP)) {
                            offsetY -= speed;
                        }
                        else if (commands.contains(MOVE_DOWN)) {
                            offsetY += speed;
                        }

                        if (commands.contains(MOVE_LEFT)) {
                            offsetX -= speed;
                        }
                        else if (commands.contains(MOVE_RIGHT)) {
                            offsetX += speed;
                        }

                        if (commands.contains(ACTION)) {
                            shoot();
                        }

                        pos.addOffset(Offset.of(offsetX, offsetY));
                        var renderer = getComponent(Renderer.class);

                        if (commands.contains(MOVE_LEFT) && facingRight) {
                            facingRight = false;
                            renderer.setFlipX(true);
                        }
                        else if (commands.contains(MOVE_RIGHT) && !facingRight) {
                            facingRight = true;
                            renderer.setFlipX(false);
                        }
                    }

                    private void shoot() {
                        if (arrowGO.isActive()) {
                            logger.debug("Arrow already flying...");
                            return;
                        }

                        logger.debug("Arrow fired!");

                        var arrowController = arrowGO.getComponent(ArrowController.class);
                        arrowController.setDirection(facingRight ? Direction.RIGHT : Direction.LEFT);
                        arrowController.setPosition(getPosition().getOffset());
                        arrowGO.setActive(true);
                    }
                })
                .build();

        gameManager.addGameObject(heroGO);

        GameObject banditGO = GameObjectBuilder.of(Vector.of(screenMiddleX-240, screenMiddleY-40, 0))
                .addStaticRenderer(BANDIT_IMAGE_FILE, CHARS_SPRITE_SIZE.getWidth(), CHARS_SPRITE_SIZE.getHeight())
                .addSquareCollider(0, "enemies", 50, Set.of(), Rect.of(20, 5, 40, 70))
                .addCollisionHandler((s, t) -> {})
                .addCollisionGizmos()
                .addComponent(new AbstractComponent() {

                    final int speed = 5;
                    @Override
                    public void update() {
                        super.update();

                        getPosition().addOffset(Offset.of(speed, 0));
                    }
                })
                .addIdentifier("Bandit", 2)
                .build();

        gameManager.addGameObject(banditGO);
    }

    private static class ArrowController extends AbstractComponent {
        private final int speed;
        private Direction direction = Direction.NONE;
        private boolean facingRight = true;


        ArrowController(int speed) {
            this.speed = speed;
        }

        @Override
        public void update() {
            super.update();

            if (getGameObject().isInactive()) {
                return;
            }

            logger.debug("Arrow updating");

            int increment = speed;
            if (direction == Direction.LEFT) {
                increment *= -1;
            }

            var position = getPosition();

            position.addOffset(Offset.of(increment, 0));

            // Disable if out of the screen.
            if (position.getX() < CHARS_SPRITE_SIZE.getWidth()*-1 || position.getX() >= SCREEN_WIDTH + CHARS_SPRITE_SIZE.getWidth()) {
//                position.getY() < CHARS_SPRITE_SIZE.getHeight() || position.getY() >= SCREEN_HEIGHT + CHARS_SPRITE_SIZE.getHeight()) {
                getGameObject().setActive(false);
            }
        }

        public void setDirection(final Direction direction) {
            this.direction = direction;

            if (direction == Direction.RIGHT && !facingRight) {
                facingRight = true;
                var renderer = getComponent(Renderer.class);
                renderer.setFlipX(false);
            }

            if (direction == Direction.LEFT && facingRight) {
                facingRight = false;
                var renderer = getComponent(Renderer.class);
                renderer.setFlipX(true);
            }
        }

        public void setPosition(final Offset pos) {
            getPosition().setOffset(pos);
        }
    }
}
