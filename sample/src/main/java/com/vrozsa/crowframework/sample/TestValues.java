package com.vrozsa.crowframework.sample;

import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public
class TestValues {
    public static final String BACKGROUND_IMAGE_FILE = "/assets/images/test_bg_1920x1080.png";
    public static final String HERO_IMAGE_FILE = "/assets/images/archer01_80x80.png";
    public static final String BANDIT_IMAGE_FILE = "/assets/images/bandit03_80x80.png";
    public static final String ARROW_IMAGE_FILE = "/assets/images/arrow_80x80.png";
    public static final Size CHARS_SPRITE_SIZE = Size.of(80, 80);
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
}
