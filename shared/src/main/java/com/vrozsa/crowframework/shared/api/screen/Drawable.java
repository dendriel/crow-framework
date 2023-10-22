package com.vrozsa.crowframework.shared.api.screen;

import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Scale;
import com.vrozsa.crowframework.shared.attributes.Size;

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
