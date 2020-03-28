package com.rozsa.crow.screen.ui;

import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.api.UIText;

import java.util.Objects;

public class UILabelTemplate implements UIText { // TODO: this must not implement UIText
    private String text;
    private String font;
    private int style;
    private int size;
    private Color color;
    private Rect rect;
    private int verticalAlignment;
    private int horizontalAlignment;

    public UILabelTemplate clone() {
        UILabelTemplate clone = new UILabelTemplate();
        clone.text = text;
        clone.font = font;
        clone.style = style;
        clone.size = size;
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

    public java.awt.Color getColor() {
        return color.getColor();
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
        UILabelTemplate labelData = (UILabelTemplate) o;
        return style == labelData.style &&
                size == labelData.size &&
                verticalAlignment == labelData.verticalAlignment &&
                horizontalAlignment == labelData.horizontalAlignment &&
                text.equals(labelData.text) &&
                font.equals(labelData.font) &&
                color.equals(labelData.color) &&
                rect.equals(labelData.rect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, font, style, size, color, rect, verticalAlignment, horizontalAlignment);
    }
}
