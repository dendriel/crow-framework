package com.vrozsa.crowframework.sample.games.robinwood;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.GameObjectBuilder;
import com.vrozsa.crowframework.game.component.animation.AnimatedRenderer;
import com.vrozsa.crowframework.game.component.animation.AnimationTemplate;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Vector;

import java.util.List;

public class RobinWoodGame {
    private static final String ASSETS_FOLDER = "/assets/robinwood/";
    private static final String HERO_IDLE_SPRITESHEET = ASSETS_FOLDER + "hero_idle.png";
    private static final String HERO_WALK_SPRITESHEET = ASSETS_FOLDER + "hero_walk.png";
    private static final String HERO_ATTACK_SPRITESHEET = ASSETS_FOLDER + "hero_attack.png";
    private static final int CHARACTER_SPRITE_LAYER = 100;
    public static void main(String[] args) {

        var crowEngine = CrowEngine.create();

        var heroIdleAnimTemplate = AnimationTemplate.builder()
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

        var heroWalkTemplate = AnimationTemplate.builder()
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

        var heroAttackTemplate = AnimationTemplate.builder()
                .name("attack")
                .rect(Rect.of(-20, -60, 720, 120))
                .frameRect(Rect.atOrigin(120, 120))
                .spritesheets(List.of(HERO_ATTACK_SPRITESHEET))
                .timeBetweenFrames(60)
                .firstFrame(0)
                .repeat(true)
                .intervalBeforeRepeating(0)
                .isActive(false)
                .build();


        var heroGO = GameObjectBuilder.of(Vector.of(400, 300, 0))
                .addAnimatedRenderer(CHARACTER_SPRITE_LAYER, heroIdleAnimTemplate, heroWalkTemplate, heroAttackTemplate)
                .build();

        crowEngine.addGameObject(heroGO);

        var animator = heroGO.getComponent(AnimatedRenderer.class);
        animator.setOnlyEnabled("attack");
    }
}
