package com.rozsa.crow.screen.ui.input;

import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.UIBaseComponentTemplate;
import com.rozsa.crow.screen.ui.UIComponentType;
import com.rozsa.crow.screen.ui.UIFontTemplate;
import com.rozsa.crow.screen.ui.UIToolTipTemplate;
import com.rozsa.crow.screen.ui.button.UIBorderTemplate;

public class UIInputFieldTemplate extends UIBaseComponentTemplate {
    private UIFontTemplate font;
    private Color fontColor;
    private Color backgroundColor;
    private boolean backgroundVisible;
    private int columns;
    private UIBorderTemplate border;
    private UIToolTipTemplate toolTip;
    private boolean isPasswordInput;
    private boolean isDisabled;
    private String text;

    public UIInputFieldTemplate() {
        super(UIComponentType.INPUT_FIELD);
        font = new UIFontTemplate();
        backgroundVisible = true;
        text = "";
    }

    public UIFontTemplate getFont() {
        return font;
    }

    public void setFont(UIFontTemplate font) {
        this.font = font;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public boolean isBackgroundVisible() {
        return backgroundVisible;
    }

    public void setBackgroundVisible(boolean backgroundVisible) {
        this.backgroundVisible = backgroundVisible;
    }

    public UIBorderTemplate getBorder() {
        return border;
    }

    public void setBorder(UIBorderTemplate border) {
        this.border = border;
    }

    public UIToolTipTemplate getToolTip() {
        return toolTip;
    }

    public void setToolTip(UIToolTipTemplate toolTip) {
        this.toolTip = toolTip;
    }

    public boolean isPasswordInput() {
        return isPasswordInput;
    }

    public void setPasswordInput(boolean passwordInput) {
        isPasswordInput = passwordInput;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
