package com.rozsa.sample.screen.splash;


import com.rozsa.screen.BaseView;
import com.rozsa.screen.ui.UIIcon;
import com.rozsa.screen.ui.UIIconTemplate;
import com.rozsa.shared.attributes.Rect;

class StaticView extends BaseView {
    StaticView(Rect rect) {
        super(rect);

        setupComponents(rect);
    }

    private void setupComponents(Rect rect) {
        UIIconTemplate backgroundData = new UIIconTemplate();
        backgroundData.setImageFile("/images/test_bg_1920x1080.png");
        backgroundData.setRect(rect);
        backgroundData.setReferenceSize(rect.getSize());
        UIIcon backgroundIcon = new UIIcon(backgroundData);
        addComponent(backgroundIcon);
    }
}
