package com.rozsa.screen.ui;

import com.rozsa.shared.attributes.Color;
import com.rozsa.screen.ui.button.UIBorderTemplate;

public class UIToolTipTemplate {
    private String text;
    private UIFontTemplate font;
    private Color color;
    private Color backgroundColor;
    private boolean backgroundEnabled;
    private UIBorderTemplate border;

    public UIToolTipTemplate() {
        border = new UIBorderTemplate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UIFontTemplate getFont() {
        return font;
    }

    public void setFont(UIFontTemplate font) {
        this.font = font;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isBackgroundEnabled() {
        return backgroundEnabled;
    }

    public void setBackgroundEnabled(boolean backgroundEnabled) {
        this.backgroundEnabled = backgroundEnabled;
    }

    public UIBorderTemplate getBorder() {
        return border;
    }

    public void setBorder(UIBorderTemplate border) {
        this.border = border;
    }
}
