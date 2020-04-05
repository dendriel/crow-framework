package com.rozsa.samples.forms.select;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.ViewTemplate;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.ui.UIAnimation;
import com.rozsa.crow.screen.ui.button.UIButton;
import com.rozsa.samples.JsonReader;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;
import com.rozsa.samples.TestUtils;

import java.io.IOException;
import java.util.List;

public class SelectTest {
    public void run() throws IOException {
        ScreenHandler<ScreenType> screen = TestUtils.createDefaultScreenHandler("Select test", false, true);

        Size simpleScreenSize = screen.getSize();
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize, Color.from(103, 129, 165));

        BaseView view = new BaseView(getTemplate());
        simpleScreen.addView(view);
        simpleScreen.displayView();

        setupInteraction(view);

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);
    }

    ViewTemplate getTemplate() throws IOException {
        JsonReader<ViewTemplate> reader = new JsonReader<>("/templates/select_test.json", ViewTemplate.class);
        return reader.read();
    }

    private void setupInteraction(BaseView view) {

        UIButton selectRightButton = view.getButton("selectRightButton");
        selectRightButton.addButtonPressedListener(this::switchCharacterRight, view);

        UIButton selectLeftButton = view.getButton("selectLeftButton");
        selectLeftButton.addButtonPressedListener(this::switchCharacterLeft, view);
    }

    private void switchCharacterRight(Object state) {
        BaseView view = (BaseView)state;

        List<UIAnimation> anim = view.getComponents("characterAnimation", UIAnimation.class);
        for (int i = 0; i < anim.size(); i++) {
            UIAnimation curr = anim.get(i);

            if (!curr.isEnabled()) {
                continue;
            }

            curr.hide();

            UIAnimation next = (i+1 < anim.size()) ? anim.get(i+1) : anim.get(0);
            next.show();
            break;
        }
    }

    private void switchCharacterLeft(Object state) {
        BaseView view = (BaseView)state;

        List<UIAnimation> anim = view.getComponents("characterAnimation", UIAnimation.class);
        for (int i = 0; i < anim.size(); i++) {
            UIAnimation curr = anim.get(i);

            if (!curr.isEnabled()) {
                continue;
            }

            curr.hide();

            UIAnimation next = (i-1 >= 0) ? anim.get(i-1) : anim.get(anim.size()-1);
            next.show();
            break;
        }
    }
}
