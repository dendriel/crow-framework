package com.vrozsa.crowframework.sample.engine;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.ScreenManager;
import com.vrozsa.crowframework.screen.ui.UIIcon;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Rect;
import com.vrozsa.crowframework.shared.attributes.Size;

import static com.vrozsa.crowframework.sample.TestValues.BACKGROUND_IMAGE_FILE;

public class SimpleEngineSetup {

    public static void main(String[] args) {

        var crow = CrowEngine.create(800, 600, Color.gray());

        ScreenManager screenManager = crow.getScreenManager();

        UIIcon icon = screenManager.addIcon(BACKGROUND_IMAGE_FILE, Rect.of(100, 100, 600, 400));
        icon.setSize(Size.of(100, 200));
    }
}
