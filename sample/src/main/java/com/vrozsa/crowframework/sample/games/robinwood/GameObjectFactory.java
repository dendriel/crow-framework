package com.vrozsa.crowframework.sample.games.robinwood;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.GameObjectBuilder;
import com.vrozsa.crowframework.sample.games.robinwood.components.CharacterDriver;
import com.vrozsa.crowframework.sample.games.robinwood.components.GameBoardController;
import com.vrozsa.crowframework.sample.games.robinwood.components.MovementAreaUpdater;
import com.vrozsa.crowframework.sample.games.robinwood.components.PlayerController;
import com.vrozsa.crowframework.sample.games.robinwood.components.ProjectileController;
import com.vrozsa.crowframework.sample.games.robinwood.components.ProjectileHandler;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Offsetable;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.*;

@RequiredArgsConstructor
public class GameObjectFactory {
    private static final LoggerService logger = LoggerService.of(GameObjectFactory.class);

    private final CrowEngine crowEngine;

    public GameObject createHero(Offset startingPos) {

        Offsetable renderer = crowEngine.getScreenManager().getRendererView();

        var heroGO = GameObjectBuilder.of(startingPos.getX(), startingPos.getY(), 0)
                .addAnimatedRenderer(CHARACTER_SPRITE_LAYER, getHeroAnimationTemplates())
                .addComponent(new CharacterDriver(
                        true, MOVEMENT_AXIS_SPEED, MOVEMENT_DIAGONAL_SPEED, PROJECTILE_IRON_ARROW, SHOOT_COOLDOWN, PROJECTILE_SPAWN_OFFSET))
                .addComponent(new PlayerController(crowEngine.getInputManager()))
                .addComponent(new ProjectileHandler(getProjectileSupplier()))
                .addCameraFollower(renderer, getCharScreenCenter(), getCameraFollowBox())
                .addComponent(new MovementAreaUpdater(renderer))
                .addSquareCollider(HERO_COLLISION_COOLDOWN, HERO_COLLISION_LAYER, 1, Set.of(TREE_COLLISION_LAYER, ENEMIES_COLLISION_LAYER), HERO_COLLISION_RECT)
                .addCollisionGizmos(Color.blue())
                .build();

        return heroGO;
    }

    public GameObject createGameBoard() {

        GameObject boardA = createBoard(0, 0,true);
        GameObject boardB = createBoard(BOARD_SIZE.getWidth()*-1, 0, true);

        return GameObjectBuilder.of(0,0,0)
                .addChildren(List.of(boardA, boardB))
                .addComponent(new GameBoardController(boardA, boardB, BOARD_SIZE.getWidth(), SCREEN_SIZE.getWidth(), crowEngine.getScreenManager().getRendererView()))
                .build();
    }

    private GameObject createBoard(int boardX, int boardY, boolean active) {
        
        List<GameObject> trees = new ArrayList<>();
        // Generate map trees in the top and bottom edges of the map.
        for (var i = 0; i < BOARD_SIZE.getWidth()/SPRITE_WIDTH; i++) {
            int x = i * SPRITE_WIDTH;
            int topY = 0;
            int bottomY = BOARD_SIZE.getHeight() - SPRITE_HEIGHT;

            trees.add(createTree(x, topY, active, TOP_TREE_SPRITE_LAYER, TOP_TREE_COLLISION_RECT));
            trees.add(createTree(x, bottomY, active, BOTTOM_TREE_SPRITE_LAYER, BOTTOM_TREE_COLLISION_RECT));
        }

        return GameObjectBuilder.of(boardX, boardY,0)
                .setActive(active)
                .addStaticRenderer(BACKGROUND_SPRITE_LAYER, BACKGROUND_IMAGE_FILE, BOARD_SIZE.getWidth(), BOARD_SIZE.getHeight(), true)
                .addChildren(trees)
                .build();
    }

    public GameObject createTree(int x, int y, boolean active, int spriteLayer, Rect collisionRect) {
        return GameObjectBuilder.of(x, y, 0)
                .setActive(active)
                .addStaticRenderer(spriteLayer, getRandomTreeImageFile(), SPRITE_WIDTH, SPRITE_HEIGHT)
                .addSquareCollider(0, TREE_COLLISION_LAYER, Integer.MAX_VALUE, Set.of(), collisionRect)
                .addCollisionGizmos()
                .build();
    }

    public GameObject createIronArrow() {
        var projectile = GameObjectBuilder.of(0, 0, 0)
                .setActive(false)
                .addStaticRenderer(PROJECTILE_SPRITE_LAYER, IRON_ARROW_IMAGE_FILE, SPRITE_WIDTH, SPRITE_HEIGHT)
                .addComponent(new ProjectileController(PROJECTILE_SPEED, PROJECTILE_LIFETIME))
                .build();

        crowEngine.addGameObject(projectile);

        logger.debug("Created new Iron Arrow GO");

        return projectile;
    }

    private Map<String, Supplier<GameObject>> getProjectileSupplier() {
        return Map.of(
                PROJECTILE_IRON_ARROW, this::createIronArrow
        );
    }
}
