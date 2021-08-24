package com.rozsa.samples.forms.login_from_template;

import com.rozsa.crow.screen.BaseView;
import com.rozsa.crow.screen.ViewTemplate;
import com.rozsa.crow.screen.ui.UILabel;
import com.rozsa.crow.screen.ui.button.UIButton;
import com.rozsa.crow.screen.ui.input.UIInputField;

class LoginViewFromTemplate extends BaseView {
    LoginViewFromTemplate(ViewTemplate data) {
        super(data);
    }

    UIButton getLoginButton() {
        return getButton("loginButton");
    }

    UIInputField getUsernameInput() {
        return getInputField("usernameInput");
    }

    UIInputField getPasswordInput() {
        return getInputField("passwordInput");
    }

    UILabel getFeedbackLabel() {
        return getLabel("feedbackLabel");
    }
}
