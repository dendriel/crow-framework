package com.rozsa.crow.screen.ui.button;

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
