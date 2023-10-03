package com.vrozsa.crowframework.sample.screen.splash;


import com.vrozsa.crowframework.screen.BaseView;
import com.vrozsa.crowframework.screen.ui.UIIcon;
import com.vrozsa.crowframework.screen.ui.UIIconTemplate;
import com.vrozsa.crowframework.shared.attributes.Rect;

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
