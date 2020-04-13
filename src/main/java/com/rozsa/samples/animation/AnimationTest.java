package com.rozsa.samples.animation;

import com.rozsa.crow.game.GameLoop;
import com.rozsa.crow.game.attributes.Vector;
import com.rozsa.crow.game.component.Position;
import com.rozsa.crow.game.component.StaticRenderer;
import com.rozsa.crow.game.component.animation.AnimatedRenderer;
import com.rozsa.crow.game.component.animation.Animation;
import com.rozsa.crow.game.component.animation.AnimationTemplate;
import com.rozsa.crow.screen.RendererView;
import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Sprite;
import com.rozsa.samples.JsonReader;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;
import com.rozsa.samples.TestUtils;
import com.rozsa.samples.renderer.RendererViewData;

import java.io.IOException;

public class AnimationTest {
    public void run() throws IOException, InterruptedException {
        ScreenHandler<ScreenType> screen = TestUtils.createDefaultScreenHandler("Animation test", false);

        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize, Color.from(116, 140, 171));

        RendererViewData data = getTemplate();
        RendererView view = new RendererView(data);
        simpleScreen.addView(view);
        simpleScreen.displayView();

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);

        AnimatedRenderer<CharacterAnimations> ar = setupRenderers(view, data);

        GameLoop.addOnUpdateListener(ar::update);

        do {
            Thread.sleep(3000);
            ar.run(CharacterAnimations.WALKING);
            Thread.sleep(3000);
            ar.run(CharacterAnimations.ATTACK);

        } while (true);
    }

    private AnimatedRenderer<CharacterAnimations> setupRenderers(RendererView view, RendererViewData data) {
        Position r1Pos = new Position(new Vector(680, 410, 0));
        Sprite r1Sprite = new Sprite(data.getArcherSpriteData());
        StaticRenderer r1 = new StaticRenderer(r1Pos, 0, StaticRenderer.DEFAULT_STATIC_RENDERER, false, false, r1Sprite);

        view.addRenderer(r1);

        AnimationTemplate walkAnimationTemplate = new AnimationTemplate();
        walkAnimationTemplate.setRect(new Rect(0, 0, 960, 160));
        walkAnimationTemplate.setImageFile("/images/skel_attack_960x160.png");
        walkAnimationTemplate.setFrameRect(new Rect(0, 0, 160, 160));
        walkAnimationTemplate.setTimeBetweenFrames(40);
        walkAnimationTemplate.setRepeat(false);

        Animation walk_animation = new Animation(walkAnimationTemplate);

        AnimationTemplate attackAnimationTemplate = new AnimationTemplate();
        attackAnimationTemplate.setRect(new Rect(0, 0, 960, 160));
        attackAnimationTemplate.setImageFile("/images/skel_mage_idle_960x160.png");
        attackAnimationTemplate.setFrameRect(new Rect(0, 0, 160, 160));
        attackAnimationTemplate.setTimeBetweenFrames(40);
        attackAnimationTemplate.setRepeat(true);

        Animation attack_animation = new Animation(attackAnimationTemplate);

        Position arPos = new Position(new Vector(860, 410, 0));
        AnimatedRenderer<CharacterAnimations> ar = new AnimatedRenderer<>(arPos, 0, AnimatedRenderer.DEFAULT_ANIMATED_RENDERER, false, false);

        ar.add(CharacterAnimations.ATTACK, attack_animation);
        ar.add(CharacterAnimations.WALKING, walk_animation);
        ar.run(CharacterAnimations.ATTACK);

        view.addRenderer(ar);
        return ar;
    }

    private RendererViewData getTemplate() throws IOException {
        JsonReader<RendererViewData> reader = new JsonReader<>("/templates/animation_test.json", RendererViewData.class);
        return reader.read();
    }

    private enum CharacterAnimations {
        ATTACK,
        WALKING,
    }
}
