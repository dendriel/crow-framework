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
import java.util.ArrayList;
import java.util.List;

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

        List<AnimatedRenderer<CharacterAnimations>> anims = setupRenderers(view, data);

        GameLoop.addOnUpdateListener(() -> anims.forEach(AnimatedRenderer::update));

        AnimatedRenderer<CharacterAnimations> ar = anims.get(0);
        do {
            Thread.sleep(2000);
            ar.setActive(CharacterAnimations.ATTACK, false);
            ar.setActive(CharacterAnimations.IDLE, true);
            Thread.sleep(2000);
            ar.setActive(CharacterAnimations.IDLE, false);
            ar.setActive(CharacterAnimations.ATTACK_REPEAT, true);
            Thread.sleep(2000);
            ar.setActive(CharacterAnimations.ATTACK_REPEAT, false);
            ar.setActive(CharacterAnimations.IDLE, true);
            Thread.sleep(2000);
            ar.setActive(CharacterAnimations.IDLE, false);
            ar.setActive(CharacterAnimations.ATTACK, true);

        } while (true);
    }

    private List<AnimatedRenderer<CharacterAnimations>> setupRenderers(RendererView view, RendererViewData data) {
        Position r1Pos = new Position(new Vector(680, 410, 0));
        Sprite r1Sprite = new Sprite(data.getArcherSpriteData());
        StaticRenderer r1 = new StaticRenderer(r1Pos, 0, StaticRenderer.DEFAULT_STATIC_RENDERER, false, false, r1Sprite);

        view.addRenderer(r1);

        AnimationTemplate attackData = new AnimationTemplate();
        attackData.setRect(new Rect(0, 0, 960, 160));
        List<String> attackSpritesheet = new ArrayList<>();
        attackSpritesheet.add("/images/skel_attack_960x160.png");
        attackData.setSpritesheets(attackSpritesheet);
        attackData.setFrameRect(new Rect(0, 0, 160, 160));
        attackData.setTimeBetweenFrames(40);
        attackData.setRepeat(false);

        Animation attackAnimation = new Animation(attackData);

        AnimationTemplate attackRepeatData = attackData.clone();
        attackRepeatData.setRepeat(true);
        attackRepeatData.setIntervalBeforeRepeating(300);

        Animation attackRepeatAnimation = new Animation(attackRepeatData);

        AnimationTemplate idleData = new AnimationTemplate();
        idleData.setRect(new Rect(0, 0, 960, 160));
        List<String> idleSpritesheet = new ArrayList<>();
        idleSpritesheet.add("/images/skel_mage_idle_960x160.png");
        idleData.setSpritesheets(idleSpritesheet);
        idleData.setFrameRect(new Rect(0, 0, 160, 160));
        idleData.setTimeBetweenFrames(40);
        idleData.setRepeat(true);

        Animation idleAnimation = new Animation(idleData);

        Position arPos = new Position(new Vector(860, 410, 0));
        AnimatedRenderer<CharacterAnimations> ar = new AnimatedRenderer<>(arPos, 0, AnimatedRenderer.DEFAULT_ANIMATED_RENDERER, false, false);
        ar.add(CharacterAnimations.IDLE, idleAnimation);
        ar.add(CharacterAnimations.ATTACK, attackAnimation);
        ar.add(CharacterAnimations.ATTACK_REPEAT, attackRepeatAnimation);
        ar.setActive(CharacterAnimations.ATTACK, true);


        AnimationTemplate idle2Data = idleData.clone();
        Animation idle2Animation = new Animation(idle2Data);

        Position ar2Pos = new Position(new Vector(760, 410, 0));
        AnimatedRenderer<CharacterAnimations> ar2 = new AnimatedRenderer<>(ar2Pos, 0, AnimatedRenderer.DEFAULT_ANIMATED_RENDERER, true, true);
        ar2.add(CharacterAnimations.IDLE, idle2Animation);
        ar2.setActive(CharacterAnimations.IDLE, true);

        view.addRenderer(ar);
        view.addRenderer(ar2);

        ArrayList<AnimatedRenderer<CharacterAnimations>> anims = new ArrayList<>();
        anims.add(ar);
        anims.add(ar2);

        return anims;
    }

    private RendererViewData getTemplate() throws IOException {
        JsonReader<RendererViewData> reader = new JsonReader<>("/templates/animation_test.json", RendererViewData.class);
        return reader.read();
    }

    private enum CharacterAnimations {
        IDLE,
        ATTACK,
        ATTACK_REPEAT,
    }
}
