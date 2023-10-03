package com.rozsa.screen.ui.button;

import com.rozsa.screen.ui.UIToolTip;
import com.rozsa.screen.ui.UIToolTipTemplate;

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
