package com.vrozsa.crowframework.sample.games.robinwood;

import com.vrozsa.crowframework.game.component.animation.AnimationTemplate;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

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
    private static final String SKELETON_WARRIOR_IDLE_SPRITESHEET = SPRITESHEETS_FOLDER + "skeleton_warrior_idle.png";
    private static final String SKELETON_WARRIOR_WALK_SPRITESHEET = SPRITESHEETS_FOLDER + "skeleton_warrior_walk.png";
    private static final String SKELETON_WARRIOR_ATTACK_SPRITESHEET = SPRITESHEETS_FOLDER + "skeleton_warrior_attack.png";


    public static final String BACKGROUND_IMAGE_FILE = ASSETS_FOLDER + "grass_bg_1600x600.png";
    public static final String IRON_ARROW_IMAGE_FILE = ASSETS_FOLDER + "iron_arrow.png";

    public static final List<String> TREE_IMAGE_FILES = List.of("maple_tree01.png", "maple_tree02.png", "maple_tree03.png", "pine_tree01.png", "pine_tree02.png", "pine_tree03.png") ;

    public static final Color BG_SCREEN_COLOR = Color.from(154, 194, 93); // light green
    public static final Size SCREEN_SIZE = Size.of(800, 600);
    public static final Offset MIDDLE_SCREEN_OFFSET = Offset.of(SCREEN_SIZE.getWidth()/2, SCREEN_SIZE.getHeight()/2);
    public static final Offset HERO_STARTING_OFFSET = Offset.of(360, 260);
    public static final int SPRITE_WIDTH = 80;
    public static final int SPRITE_HEIGHT = 80;
    public static Size BOARD_SIZE = Size.of(1600, 600);

    public static final int BACKGROUND_SPRITE_LAYER = 0;
    public static final int TOP_TREE_SPRITE_LAYER = 10;
    public static final int BOTTOM_TREE_SPRITE_LAYER = 1000;
    public static final int CHARACTER_SPRITE_LAYER = 100;
    public static final int PROJECTILE_SPRITE_LAYER = 900;
    public static final int HERO_AXIS_SPEED = 4;
    public static final int HERO_DIAGONAL_SPEED = 3;
    public static final int ENEMY_AXIS_SPEED = 1;
    public static final int ENEMY_DIAGONAL_SPEED = 1;
    public static final Offset ENEMY_ALIGN_OFFSET = Offset.of(30, 0);
    public static final int PROJECTILE_SPEED = 10;
    public static final int PROJECTILE_LIFETIME = 1000;
    public static final int MELEE_ATTACK_LIFETIME = 100;
    public static final Offset PROJECTILE_SPAWN_OFFSET = Offset.of(30, 25);
    public static final Offset MELEE_PROJECTILE_SPAWN_OFFSET = Offset.of(40, 25);
    public static final int SHOOT_COOLDOWN = 1000;
    public static final int HERO_COLLISION_COOLDOWN = 1000;

    public static final String PROJECTILE_IRON_ARROW = "iron_arrow";
    public static final String PROJECTILE_MELEE_ATTACK = "melee_attack";

    public static final int IRON_ARROW_DAMAGE = 10;
    public static final int MELEE_ATTACK_DAMAGE = 1;

    public static final String TREE_COLLISION_LAYER = "tree";
    public static final String HERO_COLLISION_LAYER = "hero";
    public static final String ENEMIES_COLLISION_LAYER = "enemies";
    public static final String HERO_PROJECTILE_COLLISION_LAYER = "hero_projectile";
    public static final String ENEMY_PROJECTILE_COLLISION_LAYER = "enemy_projectile";

    public static final int HERO_WEIGHT = 1;
    public static final int ENEMY_WEIGHT = 9;
    public static final int TREE_WEIGHT = 1000;

    public static final int WARRIOR_MAX_LIFE = 3;
    public static final int HERO_MAX_LIFE = 3;


    public static final Rect HERO_COLLISION_RECT = Rect.of(22, -20, 35 ,70);
    public static final Rect PROJECTILE_COLLISION_RECT = Rect.of(5, 30, 60, 20);
    public static final Rect PROJECTILE_MELEE_COLLISION_RECT = Rect.of(10, 10, 60, 60);
    public static final Rect TOP_TREE_COLLISION_RECT = Rect.of(0, 0, 80 ,40);
    public static final Rect BOTTOM_TREE_COLLISION_RECT = Rect.of(0, 40, 80 ,40);

    private static Random randomizer = new Random();

    public static Offset getCharScreenCenter() {
        int halfCharWidth = SPRITE_WIDTH / 2;
        int halfCharHeight = SPRITE_HEIGHT / 2;

        return MIDDLE_SCREEN_OFFSET.sub(Offset.of(halfCharWidth, halfCharHeight));
    }

    public static Rect getCameraFollowBox() {
        return Rect.of(0, 0, Integer.MAX_VALUE, 0);
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


    public static List<AnimationTemplate> getSkeletonWarriorAnimationTemplates() {
        var idle = AnimationTemplate.builder()
                .name("idle")
                .rect(Rect.of(-20, -60, 720, 120))
                .frameRect(Rect.atOrigin(120, 120))
                .spritesheets(List.of(SKELETON_WARRIOR_IDLE_SPRITESHEET))
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
                .spritesheets(List.of(SKELETON_WARRIOR_WALK_SPRITESHEET))
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
                .spritesheets(List.of(SKELETON_WARRIOR_ATTACK_SPRITESHEET))
                .timeBetweenFrames(20)
                .firstFrame(0)
                .repeat(false)
                .intervalBeforeRepeating(0)
                .isActive(false)
                .build();

        return List.of(idle, walk, attack);
    }

    public static String getRandomTreeImageFile() {
        int treeIdx = randomizer.nextInt(TREE_IMAGE_FILES.size());
        return ASSETS_FOLDER + TREE_IMAGE_FILES.get(treeIdx);
    }
}
