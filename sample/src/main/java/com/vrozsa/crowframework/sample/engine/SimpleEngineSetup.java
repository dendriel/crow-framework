package com.vrozsa.crowframework.sample.engine;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.shared.attributes.Color;

import static com.vrozsa.crowframework.sample.TestValues.BACKGROUND_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.HERO_IMAGE_FILE;

public class SimpleEngineSetup {

    public static void main(String[] args) {

        final int screenWidth = 800;
        final int screenHeight = 600;
        final int screenMiddleX = screenWidth / 2;
        final int screenMiddleY = screenHeight / 2;

        var crow = CrowEngine.create(screenWidth, screenHeight, Color.gray());

        var screenManager = crow.getScreenManager();
        screenManager.addIcon(BACKGROUND_IMAGE_FILE, 0, 0, screenWidth, screenHeight);

        var heroIcon = screenManager.addIcon(HERO_IMAGE_FILE,screenMiddleX-40, screenMiddleY-40, 80, 80);

    }
}
