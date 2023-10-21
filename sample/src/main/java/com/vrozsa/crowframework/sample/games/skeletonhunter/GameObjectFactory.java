package com.vrozsa.crowframework.sample.games.skeletonhunter;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.GameObjectBuilder;
import com.vrozsa.crowframework.sample.games.skeletonhunter.components.*;
import com.vrozsa.crowframework.screen.ui.UILabel;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.game.PositionComponent;
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

import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.BACKGROUND_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.BACKGROUND_SPRITE_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.BOARD_SIZE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.BOTTOM_TREE_COLLISION_RECT;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.BOTTOM_TREE_SPRITE_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.CHARACTER_SPRITE_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.ENEMIES_COLLISION_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.ENEMY_ALIGN_OFFSET;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.ENEMY_AXIS_SPEED;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.ENEMY_DIAGONAL_SPEED;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.ENEMY_PROJECTILE_COLLISION_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.ENEMY_WEIGHT;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.HERO_AXIS_SPEED;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.HERO_COLLISION_COOLDOWN;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.HERO_COLLISION_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.HERO_COLLISION_RECT;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.HERO_DIAGONAL_SPEED;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.HERO_MAX_LIFE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.HERO_PROJECTILE_COLLISION_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.HERO_WEIGHT;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.IRON_ARROW_DAMAGE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.IRON_ARROW_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.MELEE_ATTACK_DAMAGE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.MELEE_ATTACK_LIFETIME;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.MELEE_PROJECTILE_SPAWN_OFFSET;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.PROJECTILE_COLLISION_RECT;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.PROJECTILE_IRON_ARROW;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.PROJECTILE_LIFETIME;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.PROJECTILE_MELEE_ATTACK;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.PROJECTILE_MELEE_COLLISION_RECT;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.PROJECTILE_SPAWN_OFFSET;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.PROJECTILE_SPEED;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.PROJECTILE_SPRITE_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.SCREEN_SIZE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.SHOOT_COOLDOWN;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.SPRITE_HEIGHT;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.SPRITE_WIDTH;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.TOP_TREE_COLLISION_RECT;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.TOP_TREE_SPRITE_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.TREE_COLLISION_LAYER;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.TREE_WEIGHT;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.WARRIOR_MAX_LIFE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.WARRIOR_SCORE_VALUE;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.getCameraFollowBox;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.getCharScreenCenter;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.getHeroAnimationTemplates;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.getRandomTreeImageFile;
import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.getSkeletonWarriorAnimationTemplates;

@RequiredArgsConstructor
public class GameObjectFactory {
    private static final LoggerService logger = LoggerService.of(GameObjectFactory.class);

    private final CrowEngine crowEngine;

    public GameObject createHero(Offset startingPos) {
        Offsetable renderer = crowEngine.getScreenManager().getRendererView();

        var heroGO = GameObjectBuilder.of(startingPos.getX(), startingPos.getY(), 0)
                .addAnimatedRenderer(CHARACTER_SPRITE_LAYER, getHeroAnimationTemplates())
                .addComponent(new CharacterDriver(
                        true, HERO_AXIS_SPEED, HERO_DIAGONAL_SPEED, PROJECTILE_IRON_ARROW, SHOOT_COOLDOWN, PROJECTILE_SPAWN_OFFSET))
                .addComponent(new CharacterStatus(HERO_MAX_LIFE))
                .addComponent(new PlayerController(crowEngine.getInputManager()))
                .addComponent(new ProjectileHandler(getProjectileSupplier()))
                .addCameraFollower(renderer, getCharScreenCenter(), getCameraFollowBox())
                .addComponent(new MovementAreaUpdater(renderer))
                .addSquareCollider(HERO_COLLISION_COOLDOWN, HERO_COLLISION_LAYER, HERO_WEIGHT, Set.of(TREE_COLLISION_LAYER), HERO_COLLISION_RECT)
                .addCollisionGizmos(Color.blue())
                .addComponent(new PlayerHUD(createLifeLabel(), createScoreLabel()))
                .build();

        return heroGO;
    }

    private UILabel createLifeLabel() {
        return crowEngine.getScreenManager().addLabel("Lifes:", 32, Rect.of(10, 540, 100, 50));
    }

    private UILabel createScoreLabel() {
        return crowEngine.getScreenManager().addLabel("Score:", 32, Rect.of(180, 540, 300, 50));
    }

    public GameObject createGameBoard() {

        GameObject boardA = createBoard(0, 0,true);
        GameObject boardB = createBoard(BOARD_SIZE.getWidth()*-1, 0, true);

        return GameObjectBuilder.of(0,0)
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
        return GameObjectBuilder.of(x, y)
                .setActive(active)
                .addStaticRenderer(spriteLayer, getRandomTreeImageFile(), SPRITE_WIDTH, SPRITE_HEIGHT)
                .addSquareCollider(0, TREE_COLLISION_LAYER, TREE_WEIGHT, Set.of(), collisionRect)
                .addCollisionGizmos()
                .build();
    }

    public GameObject createIronArrow() {
        var projectileController = new ProjectileController(PROJECTILE_SPEED, PROJECTILE_LIFETIME, IRON_ARROW_DAMAGE);
        var projectile = GameObjectBuilder.atOrigin()
                .setActive(false)
                .addStaticRenderer(PROJECTILE_SPRITE_LAYER, IRON_ARROW_IMAGE_FILE, SPRITE_WIDTH, SPRITE_HEIGHT)
                .addComponent(projectileController)
                .addCollisionHandler(projectileController)
                .addSquareCollider(100, HERO_PROJECTILE_COLLISION_LAYER, Set.of(ENEMIES_COLLISION_LAYER), PROJECTILE_COLLISION_RECT)
                .addCollisionGizmos(Color.blue())
                .addAudioPlayer(crowEngine.getAudioManager().getPlayer())
                .build();

        crowEngine.addGameObject(projectile);

        logger.debug("Created new Iron Arrow GO");

        return projectile;
    }

    public GameObject createMeleeAttack() {
        var projectileController = new ProjectileController(0, MELEE_ATTACK_LIFETIME, MELEE_ATTACK_DAMAGE);
        var projectile = GameObjectBuilder.atOrigin()
                .setActive(false)
                .addComponent(projectileController)
                .addCollisionHandler(projectileController)
                .addSquareCollider(100, ENEMY_PROJECTILE_COLLISION_LAYER, Set.of(HERO_COLLISION_LAYER), PROJECTILE_MELEE_COLLISION_RECT)
                .addCollisionGizmos(Color.red())
                .addAudioPlayer(crowEngine.getAudioManager().getPlayer())
                .build();

        crowEngine.addGameObject(projectile);

        logger.debug("Created new Melee Attack GO");

        return projectile;
    }

    // Dynamic factory?
    private Map<String, Supplier<GameObject>> getProjectileSupplier() {
        return Map.of(
                PROJECTILE_IRON_ARROW, this::createIronArrow,
                PROJECTILE_MELEE_ATTACK, this::createMeleeAttack
        );
    }

    public EnemyWarriorController createSkeletonWarrior(int x, int y) {
        var enemyGO = GameObjectBuilder.of(x, y)
                .setActive(false)
                .addAnimatedRenderer(CHARACTER_SPRITE_LAYER, getSkeletonWarriorAnimationTemplates())
                .addComponent(new CharacterDriver(
                        true, ENEMY_AXIS_SPEED, ENEMY_DIAGONAL_SPEED, PROJECTILE_MELEE_ATTACK, SHOOT_COOLDOWN, MELEE_PROJECTILE_SPAWN_OFFSET))
                .addComponent(new CharacterStatus(WARRIOR_MAX_LIFE, WARRIOR_SCORE_VALUE))
                .addComponent(new EnemyWarriorController(ENEMY_ALIGN_OFFSET))
                .addComponent(new ProjectileHandler(getProjectileSupplier()))
                // Do not collide with tree to avoid getting stuck
                .addSquareCollider(1000, ENEMIES_COLLISION_LAYER, ENEMY_WEIGHT, Set.of(HERO_PROJECTILE_COLLISION_LAYER), HERO_COLLISION_RECT)
                .addCollisionGizmos(Color.red())
                .build();

        return enemyGO.getComponent(EnemyWarriorController.class);
    }

    public GameObject createEnemySpawner(PositionComponent target) {
        return GameObjectBuilder.atOrigin()
                .addComponent(new EnemySpawner(this, crowEngine.getGameManager(), target))
                .build();
    }
}
