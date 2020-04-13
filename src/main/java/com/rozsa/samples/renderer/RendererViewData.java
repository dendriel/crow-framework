package com.rozsa.samples.renderer;

import com.rozsa.crow.screen.ViewTemplate;
import com.rozsa.crow.screen.sprite.SpriteTemplate;

public class RendererViewData extends ViewTemplate {
    private SpriteTemplate archerSpriteData;

    public SpriteTemplate getArcherSpriteData() {
        return archerSpriteData;
    }

    public void setArcherSpriteData(SpriteTemplate archerSpriteData) {
        this.archerSpriteData = archerSpriteData;
    }
}
