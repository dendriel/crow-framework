package com.vrozsa.crowframework.screen.ui.components.templates;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.util.Objects;

/**
 * Create a new font template.
 */
public final class UIFontTemplate {
    private String font;
    private int style;
    private int size;
    private boolean underline;

    public UIFontTemplate() {
        font = "Serif";
        style = 0;
        size = 24;
        underline = false;
    }

    public UIFontTemplate(int size) {
        this("Serif", 0, size, false);
    }

    /**
     * @param font font type. Default available types are: Serif, SansSerif, Dialog, DialogInput and Monospaced.
     * @param style font style. 0 = no style (default); 1 = bold; 2 = italic; 3 = bold and italic.
     * @param size font size.
     * @param underline font has underline. Unavailable for Monospaced and DialogInput font types.
     */
    public UIFontTemplate(String font, int style, int size, boolean underline) {
        this.font = font;
        this.style = style;
        this.size = size;
        this.underline = underline;
    }

    @Override
    public UIFontTemplate clone() {
        return new UIFontTemplate(font, style, size, underline);
    }

    public Font getJFont() {
        var jFont = new Font(font, style, size);
        if (!underline) {
            return jFont;
        }

        return jFont
                .deriveFont(Map.of(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON));
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

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public static UIFontTemplate updateFontTemplate(UIFontTemplate font, int originWidth, int targetWidth) {
        int newFontSize = (int)(((double)font.getSize() / originWidth) * targetWidth);
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
