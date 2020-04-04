package com.rozsa.samples.form;

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

class LoginView extends BaseView {
    LoginView(Rect rect) {
        super(rect);
        setupComponents(rect);
    }

    private void setupComponents(Rect rect) {

        // Dynamic Label
        UILabelTemplate labelData = new UILabelTemplate();
        labelData.setText("Username:");
        labelData.setColor(new Color(255, 255, 255));
        labelData.setFont(new UIFontTemplate("Serif", 0, 24));
        labelData.setVerticalAlignment(0);
        labelData.setHorizontalAlignment(0);
        labelData.setRect(new Rect(138, 200, 110, 30));
        UILabel label = new UILabel(labelData);
        addComponent(label);


        UIInputFieldTemplate inputTemplate = new UIInputFieldTemplate();
        Rect inputRect = new Rect(250, 200, 200, 30);
        inputTemplate.setRect(inputRect);
        inputTemplate.setFontColor(new Color(53, 67, 87));
        inputTemplate.setBackgroundColor(new Color(255, 255, 255));
        inputTemplate.setColumns(32);
        inputTemplate.setFont(new UIFontTemplate("Serif", 0, 24));

        UIBorderTemplate border = new UIBorderTemplate();
        border.setStyle(UIBorderTemplate.BorderStyle.LINE);
        border.setColorA(new Color(53,67,87));
        border.setThickness(2);
        inputTemplate.setBorder(border);

        UIInputField input = new UIInputField(inputTemplate);
        addComponent(input);


        // Dynamic Label
        UILabelTemplate passwordLabelData = new UILabelTemplate();
        passwordLabelData.setText("Password:");
        passwordLabelData.setColor(new Color(255, 255, 255));
        passwordLabelData.setFont(new UIFontTemplate("Serif", 0, 24));
        passwordLabelData.setVerticalAlignment(0);
        passwordLabelData.setHorizontalAlignment(0);
        passwordLabelData.setRect(new Rect(146, 240, 100, 30));
        addComponent(new UILabel(passwordLabelData));


        UIInputFieldTemplate passwordTemplate = new UIInputFieldTemplate();
        Rect passwordInputRect = new Rect(250, 240, 200, 30);
        passwordTemplate.setRect(passwordInputRect);
        passwordTemplate.setFontColor(new Color(53, 67, 87));
        passwordTemplate.setBackgroundColor(new Color(255, 255, 255));
        passwordTemplate.setColumns(32);
        passwordTemplate.setFont(new UIFontTemplate("Serif", 0, 24));

        passwordTemplate.setBorder(border);
        addComponent(new UIInputField(passwordTemplate));
    }
}
