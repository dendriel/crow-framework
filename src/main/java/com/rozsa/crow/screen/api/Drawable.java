package com.rozsa.crow.screen.api;

import com.rozsa.crow.game.component.Renderer;
import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Scale;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Image;

public interface Drawable {
    void setRenderer(Renderer renderer);

    Renderer getRenderer();

    int getOrder();

    Image getImage();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isFlipX();

    boolean isFlipY();

    Scale getScale();

    Size getSize();

    Offset getOffset();
}
