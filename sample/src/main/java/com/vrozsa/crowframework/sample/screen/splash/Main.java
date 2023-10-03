package com.vrozsa.crowframework.sample.screen.splash;

import com.vrozsa.crowframework.screen.builder.ScreenHandlerFactory;

import static com.vrozsa.crowframework.sample.TestValues.BACKGROUND_IMAGE_FILE;

public class Main {
    public static void main(String[] args) {
        var screenHandler = ScreenHandlerFactory.createWithSimpleScreen(BACKGROUND_IMAGE_FILE, false);
        screenHandler.setVisible(true);
    }
}