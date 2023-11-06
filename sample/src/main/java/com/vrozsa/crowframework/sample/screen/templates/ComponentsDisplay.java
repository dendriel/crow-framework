package com.vrozsa.crowframework.sample.screen.templates;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.CrowEngineConfig;
import com.vrozsa.crowframework.engine.WindowMode;
import com.vrozsa.crowframework.sample.JsonReader;
import com.vrozsa.crowframework.screen.views.UIView;
import com.vrozsa.crowframework.screen.views.UIViewTemplate;

import java.io.IOException;

import static com.vrozsa.crowframework.sample.TestValues.BG_COLOR;


public class ComponentsDisplay {

    public static void main(String[] args) throws IOException {

        var loginTemplate = JsonReader.read("/assets/templates/components_display.json", UIViewTemplate.class);
        var rect = loginTemplate.getRect();

        var crowEngine = CrowEngine.create(CrowEngineConfig.builder()
                .color(BG_COLOR)
                .title("Components Display")
                .windowResizable(true)
                .windowMode(WindowMode.DEFAULT_SCREEN)
                .windowSize(rect.getSize())
                .build());

        // TODO: improve fill bar resizing.

        var loginView = new ComponentsDisplayView(loginTemplate);
        crowEngine.getScreenManager().addView(loginView);
    }

    private static class ComponentsDisplayView extends UIView {
        public ComponentsDisplayView(UIViewTemplate template) {
            super(template);
        }
    }
}
