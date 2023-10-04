package com.vrozsa.crowframework.shared.api.screen;

import com.vrozsa.crowframework.shared.image.DrawableSprite;
import com.vrozsa.crowframework.shared.templates.SpriteTemplate;

/**
 * Represents an image to be displayed in-game (non-UI).
 */
public interface Sprite extends Drawable {

    /**
     * Creates a new Sprite.
     * @param data template data.
     * @param image sprite target image.
     * @param isEnabled the sprite is enabled?
     * @return the new Sprite.
     */
    static Sprite of(final SpriteTemplate data, final Image image, final boolean isEnabled) {
        return new DrawableSprite(data, image, isEnabled);
    }
}
