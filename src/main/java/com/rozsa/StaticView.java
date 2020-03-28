package com.rozsa;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.UIIcon;
import com.rozsa.crow.screen.ui.UIIconTemplate;

class StaticView extends BaseView {
    protected UIIcon backgroundIcon;
    public StaticView(Rect rect) {
        super(rect);

        setupComponents(rect);
    }

    protected void setupComponents(Rect rect) {
        UIIconTemplate backgroundData = new UIIconTemplate();
        backgroundData.setImageFile("test_bg_1920x1080.png");
        backgroundData.setRect(rect);
        backgroundIcon = new UIIcon(backgroundData);
        addComponent(backgroundIcon);
    }
}
