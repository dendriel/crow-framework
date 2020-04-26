package com.rozsa.samples.components.labelgroup;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ViewTemplate;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.ui.Text;
import com.rozsa.crow.screen.ui.UILabelGroup;
import com.rozsa.crow.screen.ui.api.UIText;
import com.rozsa.samples.JsonReader;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;
import com.rozsa.samples.TestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LabelGroupTest {
    public void run() throws IOException {
        ScreenHandler<ScreenType> screen = TestUtils.createDefaultScreenHandler("Login test", false);
        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize, Color.from(116, 140, 171));

        BaseView view = new BaseView(getTemplate());
        simpleScreen.addView(view);
        simpleScreen.displayView();
        view.draw();

        UILabelGroup labelGroup = view.getLabelGroup("myHorizontalLabelGroup");
        List<UIText> texts = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            texts.add(new Text("Dendriel"));
        }
        labelGroup.set(texts);

        labelGroup = view.getLabelGroup("myVerticalLabelGroup");
        texts.clear();
        for (int i = 0; i < 8; i++) {
            texts.add(new Text("Rozsantares"));
        }
        labelGroup.set(texts);

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);
    }

    public ViewTemplate getTemplate() throws IOException {
        JsonReader<ViewTemplate> reader = new JsonReader<>("/templates/label_group_test.json", ViewTemplate.class);
        return reader.read();
    }
}
