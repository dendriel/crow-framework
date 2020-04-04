package com.rozsa.samples.components.input;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.UIFontTemplate;
import com.rozsa.crow.screen.ui.UILabel;
import com.rozsa.crow.screen.ui.UILabelTemplate;
import com.rozsa.crow.screen.ui.UIToolTipTemplate;
import com.rozsa.crow.screen.ui.button.UIBorderTemplate;
import com.rozsa.crow.screen.ui.input.UIInputField;
import com.rozsa.crow.screen.ui.input.UIInputFieldTemplate;
import com.rozsa.crow.screen.ui.listener.UIEventListener;

class InputView extends BaseView {
    InputView(Rect rect) {
        super(rect);

        setupComponents(rect);
    }

    private void setupComponents(Rect rect) {

        UIInputFieldTemplate inputTemplate = new UIInputFieldTemplate();
        Rect inputRect = new Rect(100, 100, 200, 30);
        inputTemplate.setRect(inputRect);
        inputTemplate.setFontColor(new Color(0, 0, 200));
        inputTemplate.setBackgroundColor(new Color(255, 255, 0));
        inputTemplate.setColumns(10);
        inputTemplate.setFont(new UIFontTemplate("Serif", 2, 30));
        UIBorderTemplate border = new UIBorderTemplate();
        border.setStyle(UIBorderTemplate.BorderStyle.LINE);
        border.setColorA(new Color(255,0,100));
        border.setThickness(2);
        inputTemplate.setBorder(border);
        // If password, it hides the text.
//        inputTemplate.setPasswordInput(true);

        UIToolTipTemplate toolTip = new UIToolTipTemplate();
        toolTip.setText("Hit enter to submit");
        toolTip.setBackgroundColor(new Color(0, 100, 0));
        toolTip.setBackgroundEnabled(true);
        toolTip.setColor(new Color(255, 255, 255));
        toolTip.setFont("Serif");
        toolTip.setStyle(0);
        toolTip.setSize(24);

        inputTemplate.setToolTip(toolTip);

        UIInputField input = new UIInputField(inputTemplate);
        addComponent(input);

        UILabelTemplate labelTemplate = new UILabelTemplate();
        labelTemplate.setRect(new Rect(200, 200, 200, 30));
        UILabel label = new UILabel(labelTemplate);
        label.setText("Input Value:");
        addComponent(label);

        UIEventListener listener = (e) -> { label.setText("Input Value: " + input.getText()); };
        input.addInputFieldSubmittedListener(listener, "data");
    }
}
