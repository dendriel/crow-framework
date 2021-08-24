package com.rozsa.samples.renderer;

import com.rozsa.crow.game.attributes.Vector;
import com.rozsa.crow.game.component.Position;
import com.rozsa.crow.game.component.StaticRenderer;
import com.rozsa.crow.screen.RendererView;
import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.sprite.Sprite;
import com.rozsa.samples.JsonReader;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;
import com.rozsa.samples.TestUtils;

import java.io.IOException;

public class RendererViewTest {
    public void run() throws IOException {
        ScreenHandler<ScreenType> screen = TestUtils.createDefaultScreenHandler("Renderer test", false);

        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize, Color.from(116, 140, 171));

        RendererViewData data = getTemplate();
        RendererView view = new RendererView(data);
        simpleScreen.addView(view);
        simpleScreen.displayView();

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);

        setupRenderers(view, data);
    }

    private void setupRenderers(RendererView view, RendererViewData data) {
        Position r1Pos = new Position(new Vector(760, 410, 0));
        Sprite r1Sprite = new Sprite(data.getArcherSpriteData());
        StaticRenderer r1 = new StaticRenderer(r1Pos, 0, StaticRenderer.DEFAULT_STATIC_RENDERER, false, false, r1Sprite);

        view.addRenderer(r1);
    }

    private RendererViewData getTemplate() throws IOException {
        JsonReader<RendererViewData> reader = new JsonReader<>("/templates/renderer_view_test.json", RendererViewData.class);
        return reader.read();
    }
}
