package com.vrozsa.crowframework.shared.api.screen;

import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Size;

import java.util.List;

/**
 * Represents a game object renderer component.
 */
public interface Renderer {
    Offset getPos();

    boolean isFlipX();

    boolean isFlipY();

    void setFlipX(boolean flipX);

    void setFlipY(boolean flipY);

    void setEnabled(boolean enabled);

    boolean isDisabled();

    GameObject getGameObject();

    int getLayer();

    Size getSize();

    List<Drawable> getDrawings(boolean filterInactive);

    void addRendererChangedListener(RendererObserver observer);

    void removeRendererChangedListener(RendererObserver observer);

    /**
     * Tells if this renderer should be always displaying. This avoids the rendering optimization that hides sprites that
     * are out of the screen view.
     * Useful for sprites bigger than the screen.
     * @return true if this renderer should always be drawn; false otherwise.
     */
    boolean alwaysRender();
}
