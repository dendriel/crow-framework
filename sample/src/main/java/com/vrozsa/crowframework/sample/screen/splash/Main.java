package com.vrozsa.crowframework.sample.screen.splash;

import com.vrozsa.crowframework.screen.builder.ScreenHandlerFactory;

public class Main {
    public static void main(String[] args) {
        var screenHandler = ScreenHandlerFactory.createWithSimpleScreen("/images/test_bg_1920x1080.png", false);
        screenHandler.setVisible(true);
    }
}