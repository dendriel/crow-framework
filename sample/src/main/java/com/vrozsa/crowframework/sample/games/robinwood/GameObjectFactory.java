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
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.BACKGROUND_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.BACKGROUND_SPRITE_LAYER;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.BOARD_SIZE;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.CHARACTER_SPRITE_LAYER;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.IRON_ARROW_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.MOVEMENT_AXIS_SPEED;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.MOVEMENT_DIAGONAL_SPEED;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.PROJECTILE_IRON_ARROW;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.PROJECTILE_LIFETIME;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.PROJECTILE_SPAWN_OFFSET;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.PROJECTILE_SPEED;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.PROJECTILE_SPRITE_LAYER;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.SCREEN_SIZE;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.SHOOT_COOLDOWN;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.SPRITE_HEIGHT;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.SPRITE_WIDTH;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.TREE_SPRITE_LAYER;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.getCameraFollowBox;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.getCharScreenCenter;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.getHeroAnimationTemplates;
import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.getRandomTreeImageFile;

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

            trees.add(createTree(x, topY, active));
            trees.add(createTree(x, bottomY, active));
        }

        return GameObjectBuilder.of(boardX, boardY,0)
                .setActive(active)
                .addStaticRenderer(BACKGROUND_SPRITE_LAYER, BACKGROUND_IMAGE_FILE, BOARD_SIZE.getWidth(), BOARD_SIZE.getHeight(), true)
                .addChildren(trees)
                .build();
    }

    public GameObject createTree(int x, int y, boolean active) {
        return GameObjectBuilder.of(x, y, 0)
                .setActive(active)
                .addStaticRenderer(TREE_SPRITE_LAYER, getRandomTreeImageFile(), SPRITE_WIDTH, SPRITE_HEIGHT)
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
