package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.ui.button.UIBorderTemplate;

public class UIToolTipTemplate {
    private String text;
    private String font;
    private int style;
    private int size;
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

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
