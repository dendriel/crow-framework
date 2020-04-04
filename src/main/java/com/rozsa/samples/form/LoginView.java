package com.rozsa.samples.form;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.ui.UIFontTemplate;
import com.rozsa.crow.screen.ui.UILabel;
import com.rozsa.crow.screen.ui.UILabelTemplate;
import com.rozsa.crow.screen.ui.button.UIBorderTemplate;
import com.rozsa.crow.screen.ui.button.UIButton;
import com.rozsa.crow.screen.ui.button.UIButtonTemplate;
import com.rozsa.crow.screen.ui.input.UIInputField;
import com.rozsa.crow.screen.ui.input.UIInputFieldTemplate;

class LoginView extends BaseView {
    private UIButton loginButton;
    private UIInputField usernameInput;
    private UIInputField passwordInput;
    private UILabel feedbackLabel;

    LoginView(Rect rect) {
        super(rect);
        setupComponents(rect);
    }

    private void setupComponents(Rect rect) {

        UILabelTemplate titleLabelData = new UILabelTemplate();
        titleLabelData.setText("Login Form");
        titleLabelData.setFont(new UIFontTemplate("Serif", 0, 40));
        titleLabelData.setHorizontalAlignment(0);
        titleLabelData.setRect(new Rect(rect.getWidth()/2 - 100, 30, 200, 60));
        addComponent(new UILabel(titleLabelData));

        UILabelTemplate labelData = new UILabelTemplate();
        labelData.setText("Username:");
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

        usernameInput = new UIInputField(inputTemplate);
        addComponent(usernameInput);


        // Dynamic Label
        UILabelTemplate passwordLabelData = new UILabelTemplate();
        passwordLabelData.setText("Password:");
        passwordLabelData.setRect(new Rect(146, 240, 100, 30));
        addComponent(new UILabel(passwordLabelData));


        UIInputFieldTemplate passwordTemplate = new UIInputFieldTemplate();
        Rect passwordInputRect = new Rect(250, 240, 200, 30);
        passwordTemplate.setRect(passwordInputRect);
        passwordTemplate.setFontColor(new Color(53, 67, 87));
        passwordTemplate.setBackgroundColor(new Color(255, 255, 255));
        passwordTemplate.setColumns(32);
        passwordTemplate.setFont(new UIFontTemplate("Serif", 0, 24));
        passwordTemplate.setPasswordInput(true);

        passwordTemplate.setBorder(border);
        passwordInput = new UIInputField(passwordTemplate);
        addComponent(passwordInput);

        // Login button.
        UILabelTemplate labelTemplate = new UILabelTemplate();
        labelTemplate.setText("Login");

        UIButtonTemplate buttonData = new UIButtonTemplate();
        buttonData.setRect(new Rect(350, 280, 100, 38));
        buttonData.setLabel(labelTemplate);
        buttonData.setDefaultImage("/images/button.png");
        buttonData.setPressedImage("/images/button_pressed.png");
        buttonData.setRolloverImage("/images/button_highlight.png");
        buttonData.setDisabledImage("/images/button_disabled.png");

        loginButton = new UIButton(buttonData);
        addComponent(loginButton);


        UILabelTemplate feedbackLabelData = new UILabelTemplate();
//        feedbackLabelData.setText("Logging in...");
        feedbackLabelData.setFont(new UIFontTemplate("Serif", 2, 20));
        feedbackLabelData.setRect(new Rect(250, 340, 300, 30));
        feedbackLabelData.setHorizontalAlignment(2);
        feedbackLabel  = new UILabel(feedbackLabelData);
        addComponent(feedbackLabel);
    }

    UIButton getLoginButton() {
        return loginButton;
    }

    UIInputField getUsernameInput() {
        return usernameInput;
    }

    UIInputField getPasswordInput() {
        return passwordInput;
    }

    UILabel getFeedbackLabel() {
        return feedbackLabel;
    }
}
