package com.rozsa.crow.game.api;

import com.rozsa.crow.game.component.Renderer;

public interface RendererObserver {
    void rendererChanged(Renderer renderer);
}
