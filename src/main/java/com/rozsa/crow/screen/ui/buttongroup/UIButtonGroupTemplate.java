package com.rozsa.crow.screen.ui.buttongroup;

import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.ui.UIBaseComponentTemplate;
import com.rozsa.crow.screen.ui.UIIconTemplate;
import com.rozsa.crow.screen.ui.button.UIButtonTemplate;

import static com.rozsa.crow.screen.ui.UIComponentType.BUTTON_GROUP;

public class UIButtonGroupTemplate extends UIBaseComponentTemplate {
    private UIButtonTemplate button;
    private UIIconTemplate background;
    private Size size;
    private Size spacing;

    public UIButtonGroupTemplate() {
        super(BUTTON_GROUP);
    }

    public UIButtonTemplate getButton() {
        return button;
    }

    public void setButton(UIButtonTemplate button) {
        this.button = button;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getWidth() {
        return size.getWidth();
    }

    public int getHeight() {
        return size.getHeight();
    }

    public Size getSpacing() {
        return spacing;
    }

    public void setSpacing(Size spacing) {
        this.spacing = spacing;
    }

    public UIIconTemplate getBackground() {
        return background;
    }

    public void setBackground(UIIconTemplate background) {
        this.background = background;
    }
}
