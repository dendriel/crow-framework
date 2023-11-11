package com.vrozsa.crowframework.sample.screen.templates;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.CrowEngineConfig;
import com.vrozsa.crowframework.engine.WindowMode;
import com.vrozsa.crowframework.sample.JsonReader;
import com.vrozsa.crowframework.screen.views.UIView;
import com.vrozsa.crowframework.screen.views.UIViewTemplate;

import java.io.IOException;

import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.BG_SCREEN_COLOR;

public class Inventory {

    public static void main(String[] args) throws IOException, InterruptedException {

        var loginTemplate = JsonReader.read("/assets/templates/inventory.json", UIViewTemplate.class);
        var rect = loginTemplate.getRect();

        var crowEngine = CrowEngine.create(CrowEngineConfig.builder()
                        .color(BG_SCREEN_COLOR)
                        .title("Inventory Sample")
                        .windowResizable(true)
                        .windowMode(WindowMode.DEFAULT_SCREEN)
                        .windowSize(rect.getSize())
                        .build());

        var inventoryView = new InvetoryView(loginTemplate);
        crowEngine.getScreenManager().addView(inventoryView);
        inventoryView.draw();
    }

    private static class InvetoryView extends UIView {
        public InvetoryView(UIViewTemplate template) {
            super(template);
        }
    }
}
