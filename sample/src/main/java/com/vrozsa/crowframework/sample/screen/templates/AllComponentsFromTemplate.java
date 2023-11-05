package com.vrozsa.crowframework.sample.screen.templates;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.CrowEngineConfig;
import com.vrozsa.crowframework.engine.WindowMode;
import com.vrozsa.crowframework.sample.JsonReader;
import com.vrozsa.crowframework.screen.views.UIView;
import com.vrozsa.crowframework.screen.views.UIViewTemplate;

import java.io.IOException;

import static com.vrozsa.crowframework.sample.games.skeletonhunter.ConfigurationManager.BG_SCREEN_COLOR;

public class AllComponentsFromTemplate {

    public static void main(String[] args) throws IOException {

        var loginTemplate = JsonReader.read("/assets/templates/all_ui_components.json", UIViewTemplate.class);
        var rect = loginTemplate.getRect();

        var crowEngine = CrowEngine.create(CrowEngineConfig.builder()
                .color(BG_SCREEN_COLOR)
                .title("UI Template Loading Sample")
                .windowResizable(true)
                .windowMode(WindowMode.DEFAULT_SCREEN)
                .windowSize(rect.getSize())
                .build());


        // TODO: add default font types options to the view
        var loginView = new LoginView(loginTemplate);
        crowEngine.getScreenManager().addView(loginView);
    }

    private static class LoginView extends UIView {
        public LoginView(UIViewTemplate template) {
            super(template);
        }
    }
}
