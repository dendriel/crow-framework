package com.rozsa.shared.api.screen;

import com.rozsa.shared.attributes.Offset;
import com.rozsa.shared.attributes.Scale;
import com.rozsa.shared.attributes.Size;

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
