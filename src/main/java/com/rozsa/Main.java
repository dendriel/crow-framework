package com.rozsa;

import com.rozsa.samples.forms.login.LoginTest;
import com.rozsa.samples.forms.login_from_template.LoginFromTemplateTest;

import java.io.IOException;

public class Main {
    // testing purposes
    public static void main(String[] args) throws IOException {
//        SplashSetupTest splashSetupTest = new SplashSetupTest(false);
//        splashSetupTest.run();

//        ButtonTest buttonTest = new ButtonTest();
//        buttonTest.run();

//        InputTest inputTest = new InputTest();
//        inputTest.run();

//        LoginTest loginTest = new LoginTest();
//        loginTest.run();

        LoginFromTemplateTest loginFromTemplate = new LoginFromTemplateTest();
        loginFromTemplate.run();
    }
}

