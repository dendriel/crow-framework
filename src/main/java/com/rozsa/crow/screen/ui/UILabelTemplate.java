package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.api.UIText;

import java.util.Objects;

public class UILabelTemplate extends UIBaseComponentTemplate implements UIText { // TODO: this must not implement UIText
    private String text;
    private UIFontTemplate font;
    private Color color;
    private Rect rect;
    private int verticalAlignment;
    private int horizontalAlignment;

    public UILabelTemplate() {
        super(UIComponentType.LABEL);
        color = new Color();
        font = new UIFontTemplate();
    }

    public UILabelTemplate clone() {
        UILabelTemplate clone = new UILabelTemplate();
        clone.font = font.clone();
        clone.color = color.clone();
        clone.rect = rect.clone();
        clone.verticalAlignment = verticalAlignment;
        clone.horizontalAlignment = horizontalAlignment;
        return clone;
    }

    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public int getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(int verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public String getValue() {
        return text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UIFontTemplate getFont() {
        return font.clone();
    }

    public void setFont(UIFontTemplate font) {
        this.font = font;
    }

    public java.awt.Color getColor() {
        return color.getJColor();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UILabelTemplate that = (UILabelTemplate) o;
        return verticalAlignment == that.verticalAlignment &&
                horizontalAlignment == that.horizontalAlignment &&
                Objects.equals(text, that.text) &&
                Objects.equals(font, that.font) &&
                Objects.equals(color, that.color) &&
                Objects.equals(rect, that.rect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, font, color, rect, verticalAlignment, horizontalAlignment);
    }
}
