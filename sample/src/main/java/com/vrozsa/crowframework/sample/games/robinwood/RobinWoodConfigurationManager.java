package com.vrozsa.crowframework.sample.games.robinwood;

import com.vrozsa.crowframework.game.component.animation.AnimationTemplate;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A better way to do this is to load configurations from files (json, yml, etc).
 */
@NoArgsConstructor(access =  AccessLevel.PRIVATE)
public final class RobinWoodConfigurationManager {
    private static final String ASSETS_FOLDER = "/assets/robinwood/";
    private static final String SPRITESHEETS_FOLDER = ASSETS_FOLDER + "spritesheets/";
    private static final String HERO_IDLE_SPRITESHEET = SPRITESHEETS_FOLDER + "hero_idle.png";
    private static final String HERO_WALK_SPRITESHEET = SPRITESHEETS_FOLDER + "hero_walk.png";
    private static final String HERO_ATTACK_SPRITESHEET = SPRITESHEETS_FOLDER + "hero_attack.png";


    public static final String BACKGROUND_IMAGE_FILE = ASSETS_FOLDER + "grass_bg_1600x600.png";
    public static final String IRON_ARROW_IMAGE_FILE = ASSETS_FOLDER + "iron_arrow.png";

    public static final Offset SCREEN_SIZE = Offset.of(800, 600);
    public static final Offset MIDDLE_SCREEN_OFFSET = SCREEN_SIZE.divide(Offset.of(2));
    public static final int CHAR_IMAGE_WIDTH = 80;
    public static final int CHAR_IMAGE_HEIGHT = 80;

    public static final int BACKGROUND_SPRITE_LAYER = 0;
    public static final int CHARACTER_SPRITE_LAYER = 100;
    public static final int PROJECTILE_SPRITE_LAYER = 200;
    public static final int MOVEMENT_AXIS_SPEED = 5;
    public static final int MOVEMENT_DIAGONAL_SPEED = 3;
    public static final int PROJECTILE_SPEED = 10;
    public static final Offset PROJECTILE_SPAWN_OFFSET = Offset.of(30, 25);
    public static final int SHOOT_COOLDOWN = 1000;

    public static final String PROJECTILE_IRON_ARROW = "iron_arrow";

    public static Offset getCharScreenCenter() {
        int halfCharWidth = CHAR_IMAGE_WIDTH / 2;
        int halfCharHeight = CHAR_IMAGE_HEIGHT / 2;

        return MIDDLE_SCREEN_OFFSET.sub(Offset.of(halfCharWidth, halfCharHeight));
    }

    public static List<AnimationTemplate> getHeroAnimationTemplates() {
        var idle = AnimationTemplate.builder()
                .name("idle")
                .rect(Rect.of(-20, -60, 720, 120))
                .frameRect(Rect.atOrigin(120, 120))
                .spritesheets(List.of(HERO_IDLE_SPRITESHEET))
                .timeBetweenFrames(60)
                .firstFrame(0)
                .repeat(true)
                .intervalBeforeRepeating(60)
                .isActive(true)
                .build();

        var walk = AnimationTemplate.builder()
                .name("walk")
                .rect(Rect.of(-20, -60, 720, 120))
                .frameRect(Rect.atOrigin(120, 120))
                .spritesheets(List.of(HERO_WALK_SPRITESHEET))
                .timeBetweenFrames(60)
                .firstFrame(0)
                .repeat(true)
                .intervalBeforeRepeating(0)
                .isActive(false)
                .build();

        var attack = AnimationTemplate.builder()
                .name("attack")
                .rect(Rect.of(-20, -60, 720, 120))
                .frameRect(Rect.atOrigin(120, 120))
                .spritesheets(List.of(HERO_ATTACK_SPRITESHEET))
                .timeBetweenFrames(20)
                .firstFrame(0)
                .repeat(false)
                .intervalBeforeRepeating(0)
                .isActive(false)
                .build();

        return List.of(idle, walk, attack);
    }
}
