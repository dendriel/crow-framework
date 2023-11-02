package com.vrozsa.crowframework.screen.ui.components.button;

import com.vrozsa.crowframework.screen.ui.components.UIToolTip;
import com.vrozsa.crowframework.screen.ui.components.templates.UIToolTipTemplate;

import javax.swing.JButton;
import javax.swing.JToolTip;


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
