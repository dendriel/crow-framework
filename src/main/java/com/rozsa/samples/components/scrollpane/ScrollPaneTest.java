package com.rozsa.samples.components.scrollpane;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ViewTemplate;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.samples.JsonReader;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;
import com.rozsa.samples.TestUtils;

import java.io.IOException;

public class ScrollPaneTest {

    public void run() throws IOException {
        ScreenHandler<ScreenType> screen = TestUtils.createDefaultScreenHandler("Scroll Pane test", false);
        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize, Color.from(116, 140, 171));

        BaseView view = new BaseView(getTemplate());
        simpleScreen.addView(view);
        simpleScreen.displayView();
        view.draw();

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);
    }

    public ViewTemplate getTemplate() throws IOException {
        JsonReader<ViewTemplate> reader = new JsonReader<>("/templates/scroll_pane_test.json", ViewTemplate.class);
        return reader.read();
    }
}
