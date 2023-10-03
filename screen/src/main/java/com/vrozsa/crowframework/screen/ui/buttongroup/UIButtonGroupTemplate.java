package com.vrozsa.crowframework.screen.ui.buttongroup;

import com.vrozsa.crowframework.screen.ui.UIComponentType;
import com.vrozsa.crowframework.screen.ui.UIIconTemplate;
import com.vrozsa.crowframework.screen.ui.button.UIButtonTemplate;
import com.vrozsa.crowframework.shared.attributes.Border;
import com.vrozsa.crowframework.shared.attributes.Size;
import com.vrozsa.crowframework.screen.ui.UIBaseComponentTemplate;

public class UIButtonGroupTemplate extends UIBaseComponentTemplate {
    private UIButtonTemplate button;
    private UIIconTemplate background;
    private Size size;
    // Use spacing to enforce a distance between components (when the components size is bigger than
    // the rect).
    private Size spacing;
    private Border border;
    private CustomScrollBarUITemplate customScrollBarUI;

    public UIButtonGroupTemplate() {
        super(UIComponentType.BUTTON_GROUP);
        spacing = Size.zeroed();
        border = new Border();
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

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }

    public CustomScrollBarUITemplate getCustomScrollBarUI() {
        return customScrollBarUI;
    }

    public void setCustomScrollBarUI(CustomScrollBarUITemplate customScrollBarUI) {
        this.customScrollBarUI = customScrollBarUI;
    }
}
