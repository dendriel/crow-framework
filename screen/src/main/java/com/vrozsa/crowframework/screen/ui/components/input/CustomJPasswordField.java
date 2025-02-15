package com.vrozsa.crowframework.screen.ui.components.input;

import com.vrozsa.crowframework.screen.ui.components.UIToolTip;
import com.vrozsa.crowframework.screen.ui.components.templates.UIToolTipTemplate;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class CustomJPasswordField extends JPasswordField {
    private final UIToolTipTemplate toolTipTemplate;
    private final int columns;

    public CustomJPasswordField(int columns) {
        this(columns, new UIToolTipTemplate());
    }

    public CustomJPasswordField(int columns, UIToolTipTemplate toolTipTemplate) {
        super();
        this.toolTipTemplate = toolTipTemplate;
        this.columns = columns;
    }

    @Override
    public JToolTip createToolTip() {
        return (new UIToolTip(toolTipTemplate, this));
    }

    @Override
    protected Document createDefaultModel() {
        return new LimitDocument();
    }

    private class LimitDocument extends PlainDocument {
        @Override
        public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
            if (str == null) {
                return;
            }

            if ((getLength() + str.length()) <= columns) {
                super.insertString(offset, str, attr);
            }
        }
    }
}
