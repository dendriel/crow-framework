package com.rozsa.samples.form;

import com.rozsa.crow.screen.ScreenHandler;
import com.rozsa.crow.screen.attributes.Color;
import com.rozsa.crow.screen.attributes.Rect;
import com.rozsa.crow.screen.attributes.Size;
import com.rozsa.crow.screen.ui.UILabel;
import com.rozsa.crow.screen.ui.button.UIButton;
import com.rozsa.crow.screen.ui.input.UIInputField;
import com.rozsa.samples.ScreenType;
import com.rozsa.samples.SimpleScreen;
import com.rozsa.samples.TestUtils;

public class LoginTest {

    public void run() {
        ScreenHandler<ScreenType> screen = TestUtils.createDefaultScreenHandler("Login test");
        Size simpleScreenSize = screen.getSize();
//        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize, Color.from(76, 96, 125));
        SimpleScreen simpleScreen = new SimpleScreen(simpleScreenSize, Color.from(116, 140, 171));

        Rect rect = new Rect(0, 0, simpleScreenSize.getWidth(), simpleScreenSize.getHeight());
        LoginView view = new LoginView(rect);
        simpleScreen.addView(view);
        simpleScreen.displayView();

        setupViewListeners(view);

        screen.add(ScreenType.SIMPLE, simpleScreen);
        screen.setOnlyVisible(ScreenType.SIMPLE, true);
    }

    private void setupViewListeners(LoginView view) {
        UIButton loginButton = view.getLoginButton();
        loginButton.addButtonPressedListener(this::onLoginButtonPressed, view);
    }

    // QAs guys loves when we forget to lock buttons.
    void setAllDisabled(LoginView view, boolean isDisabled) {
        view.getLoginButton().setDisabled(isDisabled);
        view.getUsernameInput().setDisabled(isDisabled);
        view.getPasswordInput().setDisabled(isDisabled);
    }

    void onLoginButtonPressed(Object state) {
        LoginView view = (LoginView) state;
        UIInputField usernameInput = view.getUsernameInput();
        UIInputField passwordInput = view.getPasswordInput();

        setAllDisabled(view, true);

        UILabel feedbackLabel = view.getFeedbackLabel();

        String username = usernameInput.getText();
        if (username.isEmpty()) {
            feedbackLabel.setText("Please, fill in the username.");
            setAllDisabled(view, false);
            return;
        }

        String password = passwordInput.getText();
        if (password.isEmpty()) {
            feedbackLabel.setText("Please, fill in the password.");setAllDisabled(view, false);
            return;
        }

        feedbackLabel.setText("Logging in...");

        Runnable task = () -> {
            try {
                Thread.sleep(3000);
                feedbackLabel.setText("Success!");
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                setAllDisabled(view, false);
            }
            feedbackLabel.setText("Failure!");
        };

        new Thread(task).start();
    }
}
