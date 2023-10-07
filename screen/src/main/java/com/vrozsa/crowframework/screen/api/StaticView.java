package com.vrozsa.crowframework.screen.api;


import com.vrozsa.crowframework.screen.internal.BaseView;
import com.vrozsa.crowframework.screen.ui.UIIcon;
import com.vrozsa.crowframework.screen.ui.UIIconTemplate;
import com.vrozsa.crowframework.shared.attributes.Rect;

public class StaticView extends BaseView {
    public StaticView(final Rect rect, final String imageFile) {
        super("STATIC", rect);

        setupComponents(rect, imageFile);
    }

    private void setupComponents(final Rect rect, final String imageFile) {
        UIIconTemplate backgroundData = new UIIconTemplate();
        backgroundData.setImageFile(imageFile);
        backgroundData.setRect(rect);
        backgroundData.setReferenceSize(rect.getSize());

        UIIcon backgroundIcon = new UIIcon(backgroundData);
        addComponent(backgroundIcon);
    }
}
