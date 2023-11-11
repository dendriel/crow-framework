package com.vrozsa.crowframework.screen.ui.components;

import com.vrozsa.crowframework.screen.ui.components.templates.UIToolTipTemplate;

import javax.swing.*;
import java.awt.*;

public final class UIToolTip extends JToolTip {
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

        Font font = data.getFont().getJFont();
        setFont(font);
        setOpaque(data.isBackgroundEnabled());

        setupBorder();
    }

    private void setupBorder() {
        setBorder(data.getBorder().getJBorder());
    }
}
