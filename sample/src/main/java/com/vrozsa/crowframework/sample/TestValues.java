package com.vrozsa.crowframework.sample;

import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public
class TestValues {
    public static final String BACKGROUND_IMAGE_FILE = "/assets/images/test_bg_1920x1080.png";
    public static final String HERO_IMAGE_FILE = "/assets/images/archer01_80x80.png";
    public static final String BANDIT_IMAGE_FILE = "/assets/images/bandit03_80x80.png";
    public static final String WARRIOR_WALK_SPRITESHEET = "/assets/images/warrior_walk_960x160.png";
    public static final String ARROW_IMAGE_FILE = "/assets/images/arrow_80x80.png";
    public static final Color BG_COLOR = Color.from(51, 153, 255);
    public static final Size CHARS_SPRITE_SIZE = Size.of(80, 80);
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final Size WINDOW_SIZE = Size.of(SCREEN_WIDTH, SCREEN_HEIGHT);
    public static final Offset SCREEN_MIDDLE = Offset.of(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
}
