package com.vrozsa.crowframework.screen.ui.components.templates;

import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Border;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Data;

@Data
public final class UIButtonGroupTemplate extends UIBaseComponentTemplate {
    private UIButtonTemplate button;
    private UIIconTemplate background;
    private Size size;
    // Use spacing to enforce a distance between components (when the components size is bigger than
    // the rect).
    private Size spacing;
    private Border border;
    private CustomScrollBarUITemplate customScrollBarUI;
    // increment added via scroll button.
    private int scrollIncrement;
    // Which color to use in the background if no background image is set.
    private Color backgroundColor;

    public UIButtonGroupTemplate() {
        super(UIComponentType.BUTTON_GROUP);
        spacing = Size.zeroed();
        border = new Border();
        scrollIncrement = 10;
        backgroundColor = Color.gray();
    }
}
