package com.vrozsa.crowframework.screen.ui.components.templates;

import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.shared.api.screen.ui.UIComponentType;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.screen.ui.components.api.UIText;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;
import lombok.Builder;

import java.util.Objects;

import static java.util.Objects.isNull;

public final class UILabelTemplate extends UIBaseComponentTemplate implements UIText {
// TODO: this must not implement UIText
    private String text;
    private UIFontTemplate font;
    private Color color;
    private int verticalAlignment;
    private int horizontalAlignment;

    public UILabelTemplate() {
        super(UIComponentType.LABEL);
        color = new Color();
        font = new UIFontTemplate();
    }

    private UILabelTemplate(UILabelTemplate from) {
        super(from);

        this.font = from.font.clone();
        this.color = from.color.clone();
        this.rect = from.rect.clone();
        this.verticalAlignment = from.verticalAlignment;
        this.horizontalAlignment = from.horizontalAlignment;
    }

    @Builder
    public UILabelTemplate(
            Rect rect,
            String tag,
            UIExpandMode expandMode,
            Size referenceSize,
            Boolean isEnabled,
            String text,
            UIFontTemplate font,
            Color color,
            int verticalAlignment,
            int horizontalAlignment
    ) {
        super(UIComponentType.LABEL,
                rect,
                tag,
                isNull(expandMode) ? UIExpandMode.FILL : expandMode,
                referenceSize,
                isNull(isEnabled) || isEnabled
        );
        this.text = text;
        this.font = isNull(font) ? new UIFontTemplate() : font;
        this.color = isNull(color) ? Color.white() : color;
        this.verticalAlignment = verticalAlignment;
        this.horizontalAlignment = horizontalAlignment;
    }

    public UILabelTemplate clone() {
        return new UILabelTemplate(this);
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
