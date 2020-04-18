package com.rozsa.crow.screen.api;

import com.rozsa.crow.game.GameObject;
import com.rozsa.crow.game.api.RendererObserver;
import com.rozsa.crow.screen.attributes.Offset;
import com.rozsa.crow.screen.attributes.Size;

import java.util.List;

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
}
