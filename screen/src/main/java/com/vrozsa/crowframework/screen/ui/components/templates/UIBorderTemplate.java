package com.vrozsa.crowframework.screen.ui.components.templates;

import com.vrozsa.crowframework.shared.attributes.Color;

import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
public final class UIBorderTemplate {
    private BorderStyle style;
    private Color colorA;
    private Color colorB;
    private Color colorC;
    private Color colorD;
    private int thickness;
    private int type;

    public UIBorderTemplate() {
        style = BorderStyle.LINE;
        colorA = new Color(0, 0, 0);
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

    public Border getJBorder() {
        switch (style) {
            case ETCHED:
                return createEtchedBorder();
            case BEVEL:
                return createBevelBorder();
            case LINE:
                return new BorderUIResource.LineBorderUIResource(colorA.getJColor(), thickness);
            case NONE:
            default:
                return null;
        }
    }

    private Border createEtchedBorder() {
        if (colorA != null && colorB != null) {
            return new BorderUIResource.EtchedBorderUIResource(colorA.getJColor(), colorB.getJColor());
        }
        else if (type == 0 || type == 1) {
            return new BorderUIResource.EtchedBorderUIResource(type);
        }

        return new BorderUIResource.EtchedBorderUIResource();
    }

    private Border createBevelBorder() {
        if (colorA != null && colorB != null && colorC != null && colorD != null) {
            return new BorderUIResource.BevelBorderUIResource (type,
                    colorA.getJColor(), colorB.getJColor(), colorC.getJColor(), colorD.getJColor());
        }
        else if (colorA != null && colorB != null) {
            return new BorderUIResource.BevelBorderUIResource(type, colorA.getJColor(), colorB.getJColor());
        }
        else if (type == 0 || type == 1) {
            return new BorderUIResource.BevelBorderUIResource(type);
        }

        return new BorderUIResource.BevelBorderUIResource(0);
    }
}
