package com.rozsa.samples.splash;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.UIIcon;
import com.rozsa.crow.screen.ui.UIIconTemplate;

class StaticView extends BaseView {
    StaticView(Rect rect) {
        super(rect);

        setupComponents(rect);
    }

    private void setupComponents(Rect rect) {
        UIIconTemplate backgroundData = new UIIconTemplate();
        backgroundData.setImageFile("/images/test_bg_1920x1080.png");
        backgroundData.setRect(rect);
        UIIcon backgroundIcon = new UIIcon(backgroundData);
        addComponent(backgroundIcon);
    }
}
