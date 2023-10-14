package com.vrozsa.crowframework.sample.games.robinwood;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.GameObjectBuilder;
import com.vrozsa.crowframework.sample.games.robinwood.components.CharacterDriver;
import com.vrozsa.crowframework.sample.games.robinwood.components.PlayerController;
import com.vrozsa.crowframework.sample.games.robinwood.components.ProjectileController;
import com.vrozsa.crowframework.sample.games.robinwood.components.ProjectileHandler;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.logger.LoggerService;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Supplier;

import static com.vrozsa.crowframework.sample.games.robinwood.RobinWoodConfigurationManager.*;

@RequiredArgsConstructor
public class GameObjectFactory {
    private static final LoggerService logger = LoggerService.of(GameObjectFactory.class);

    private final CrowEngine crowEngine;

    public GameObject createHero(Offset startingPos) {
        return GameObjectBuilder.of(startingPos.getX(), startingPos.getY(), 0)
                .addAnimatedRenderer(CHARACTER_SPRITE_LAYER, getHeroAnimationTemplates())
                .addComponent(new CharacterDriver(
                        true, MOVEMENT_AXIS_SPEED, MOVEMENT_DIAGONAL_SPEED, PROJECTILE_IRON_ARROW, SHOOT_COOLDOWN, PROJECTILE_SPAWN_OFFSET))
                .addComponent(new PlayerController(crowEngine.getInputManager()))
                .addComponent(new ProjectileHandler(getProjectileSupplier()))
                .addCameraFollower(crowEngine.getScreenManager().getRendererView(), getCharScreenCenter(), getCameraFollowBox())
                .build();
    }

    public GameObject createBackground() {
        return GameObjectBuilder.of(0,0,0)
                .addStaticRenderer(BACKGROUND_SPRITE_LAYER, BACKGROUND_IMAGE_FILE, 1600, 600, true)
                .build();
    }

    public GameObject createIronArrow() {
        var projectile = GameObjectBuilder.of(0, 0, 0)
                .setActive(false)
                .addStaticRenderer(PROJECTILE_SPRITE_LAYER, IRON_ARROW_IMAGE_FILE, CHAR_IMAGE_WIDTH, CHAR_IMAGE_HEIGHT)
                .addComponent(new ProjectileController(PROJECTILE_SPEED))
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
