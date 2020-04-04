package com.rozsa.crow.screen.ui;

import java.awt.*;
import java.util.Objects;

public class UIFontTemplate {
    private String font;
    private int style;
    private int size;

    @Override
    protected UIFontTemplate clone() {
        return new UIFontTemplate(font, style, size);
    }

    public UIFontTemplate() {
        font = "Serif";
        style = 0;
        size = 24;
    }

    public UIFontTemplate(String font, int style, int size) {
        this.font = font;
        this.style = style;
        this.size = size;
    }

    public Font getJFont() {
        return new Font(font, style, size);
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

    public static UIFontTemplate updateFontTemplate(UIFontTemplate font, int originWidth, int targetWidth) {
        int newFontSize = (int)(((float)font.getSize() / originWidth) * targetWidth);
        font.setSize(newFontSize);
        return font;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UIFontTemplate that = (UIFontTemplate) o;
        return style == that.style &&
                size == that.size &&
                font.equals(that.font);
    }

    @Override
    public int hashCode() {
        return Objects.hash(font, style, size);
    }
}
