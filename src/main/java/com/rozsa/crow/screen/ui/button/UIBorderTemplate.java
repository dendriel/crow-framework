package com.rozsa.crow.screen.ui.button;

import com.rozsa.crow.screen.attributes.Color;

public class UIBorderTemplate {
    private BorderStyle style;
    private Color colorA;
    private Color colorB;
    private Color colorC;
    private Color colorD;
    private int thickness;
    private int type;

    public UIBorderTemplate() {
        style = BorderStyle.LINE;
        thickness = 1;
    }

    public BorderStyle getStyle() {
        return style;
    }

    public void setStyle(BorderStyle style) {
        this.style = style;
    }

    public Color getColorA() {
        return colorA;
    }

    /**
     * Line, Etched and Bevel styles.
     */
    public void setColorA(Color colorA) {
        this.colorA = colorA;
    }

    public Color getColorB() {
        return colorB;
    }

    /**
     * Etched and Bevel styles.
     */
    public void setColorB(Color colorB) {
        this.colorB = colorB;
    }

    public Color getColorC() {
        return colorC;
    }

    /**
     * Bevel style (optional).
     */
    public void setColorC(Color colorC) {
        this.colorC = colorC;
    }

    public Color getColorD() {
        return colorD;
    }

    /**
     * Bevel style (optional).
     */
    public void setColorD(Color colorD) {
        this.colorD = colorD;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getType() {
        return type;
    }

    /**
     * Set border type for Etched and Bevel styles. (Required if Bevel style).
     * @param type 0 = RAISED; 1 = LOWERED.
     */
    public void setType(int type) {
        this.type = type;
    }

    public enum BorderStyle {
        NONE,
        LINE,
        ETCHED,
        BEVEL,
    }
}
