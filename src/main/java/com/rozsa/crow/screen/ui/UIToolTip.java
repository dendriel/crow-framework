package com.rozsa.crow.screen.ui;

import javax.swing.*;
import java.awt.*;

public class UIToolTip extends JToolTip {
    private final UIToolTipTemplate data;

    public UIToolTip(UIToolTipTemplate data, JComponent component) {
        super();
        this.data = data;

        setComponent(component);
        setup();
    }

    private void setup() {
        setBackground(data.getBackgroundColor().getJColor());
        setForeground(data.getColor().getJColor());

        Font font = new Font(data.getFont(), data.getStyle(), data.getSize());
        setFont(font);
        setOpaque(data.isBackgroundEnabled());

        setupBorder();
    }

    private void setupBorder() {
        setBorder(data.getBorder().getJBorder());
    }
}
