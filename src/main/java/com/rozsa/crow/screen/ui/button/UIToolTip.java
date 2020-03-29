package com.rozsa.crow.screen.ui.button;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

class UIToolTip extends JToolTip {
    private final UIToolTipTemplate data;

    UIToolTip(UIToolTipTemplate data, JComponent component) {
        super();
        this.data = data;

        setComponent(component);
        setup();
    }

    private void setup() {
        setBackground(data.getBackgroundColor().getColor());
        setForeground(data.getColor().getColor());

        Font font = new Font(data.getFont(), data.getStyle(), data.getSize());
        setFont(font);
        setOpaque(data.isBackgroundEnabled());

        setupBorder();
    }

    private void setupBorder() {
        UIBorderTemplate borderTemplate = data.getBorder();
        Border border = null;
        switch (borderTemplate.getStyle()) {
            case ETCHED:
                border = createEtchedBorder(borderTemplate);
                break;
            case BEVEL:
                border = createBevelBorder(borderTemplate);
                break;
            case LINE:
                border = new BorderUIResource.LineBorderUIResource(borderTemplate.getColorA().getColor(), borderTemplate.getThickness());
                break;
            case NONE:
            default:
                break;
        }

        setBorder(border);
    }

    private Border createEtchedBorder(UIBorderTemplate borderTemplate) {
        if (borderTemplate.getColorA() != null && borderTemplate.getColorB() != null) {
            return new BorderUIResource.EtchedBorderUIResource(borderTemplate.getColorA().getColor(), borderTemplate.getColorB().getColor());
        }
        else if (borderTemplate.getType() == 0 || borderTemplate.getType() == 1) {
            return new BorderUIResource.EtchedBorderUIResource(borderTemplate.getType());
        }

        return new BorderUIResource.EtchedBorderUIResource();
    }

    private Border createBevelBorder(UIBorderTemplate borderTemplate) {
        if (borderTemplate.getColorA() != null && borderTemplate.getColorB() != null && borderTemplate.getColorC() != null && borderTemplate.getColorD() != null) {
            return new BorderUIResource.BevelBorderUIResource (
                    borderTemplate.getType(),
                    borderTemplate.getColorA().getColor(), borderTemplate.getColorB().getColor(),
                    borderTemplate.getColorC().getColor(), borderTemplate.getColorD().getColor()
            );
        }
        else if (borderTemplate.getColorA() != null && borderTemplate.getColorB() != null) {
            return new BorderUIResource.BevelBorderUIResource(borderTemplate.getType(), borderTemplate.getColorA().getColor(), borderTemplate.getColorB().getColor());
        }
        else if (borderTemplate.getType() == 0 || borderTemplate.getType() == 1) {
            return new BorderUIResource.BevelBorderUIResource(borderTemplate.getType());
        }

        return new BorderUIResource.BevelBorderUIResource(0);
    }
}
