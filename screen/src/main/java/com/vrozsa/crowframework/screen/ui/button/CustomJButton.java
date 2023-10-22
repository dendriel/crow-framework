package com.vrozsa.crowframework.screen.ui.button;

import com.vrozsa.crowframework.screen.ui.UIToolTip;
import com.vrozsa.crowframework.screen.ui.UIToolTipTemplate;

import javax.swing.*;

class CustomJButton extends JButton {
    private final UIToolTipTemplate toolTipTemplate;

    CustomJButton(UIToolTipTemplate toolTipTemplate) {
        this.toolTipTemplate = toolTipTemplate;
    }

    CustomJButton() {
        this.toolTipTemplate = new UIToolTipTemplate();
    }

    @Override
    public JToolTip createToolTip() {
        return (new UIToolTip(toolTipTemplate, this));
    }
}
