package com.vrozsa.crowframework.sample.screen.templates;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.CrowEngineConfig;
import com.vrozsa.crowframework.engine.WindowMode;
import com.vrozsa.crowframework.sample.JsonReader;
import com.vrozsa.crowframework.screen.ui.components.UIAnimation;
import com.vrozsa.crowframework.screen.views.UIView;
import com.vrozsa.crowframework.screen.views.UIViewTemplate;
import com.vrozsa.crowframework.shared.attributes.Color;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class CharacterSelectionFromTemplate {

    public static void main(String[] args) throws IOException {

        var loginTemplate = JsonReader.read("/assets/templates/character_selection.json", UIViewTemplate.class);
        var rect = loginTemplate.getRect();

        var crowEngine = CrowEngine.create(CrowEngineConfig.builder()
                .color(Color.from(60, 120, 194))
                .title("UI Template Loading Sample")
                .windowResizable(true)
                .windowMode(WindowMode.DEFAULT_SCREEN)
                .windowSize(rect.getSize())
                .build());


        var loginView = new CharacterSelectionView(loginTemplate);
        crowEngine.getScreenManager().addView(loginView);
    }

    private static class CharacterSelectionView extends UIView {
        private static final Map<String, String> ANIM_NAMES = Map.of(
                "skeletonMage", "Skeleton Mage",
                "skeletonWarrior", "Skeleton Warrior",
                "skeleton", "Skeleton"
        );

        public CharacterSelectionView(UIViewTemplate template) {
            super(template);

            setupInteractions();
        }

        private void setupInteractions() {
            var selectRightButton = getButton("selectRightButton");
            selectRightButton.addButtonPressedListener(this::switchCharacterRight, this);

            var selectLeftButton = getButton("selectLeftButton");
            selectLeftButton.addButtonPressedListener(this::switchCharacterLeft, this);

            var newButton = getButton("newButton");
            newButton.addButtonPressedListener(this::createCharacter, this);

            var clearButton = getButton("clearButton");
            clearButton.addButtonPressedListener(this::clearInterface, this);
        }

        private void createCharacter(Object state) {
            var view = (UIView)state;

            List<UIAnimation> anim = view.getComponents("characterAnimation", UIAnimation.class);
            UIAnimation activeAnimation = null;
            for (int i = 0; i < anim.size(); i++) {
                activeAnimation = anim.get(i);

                if (activeAnimation.isEnabled()) {
                    break;
                }
            }

            if (isNull(activeAnimation)) {
                return;
            }


            var newLabel = view.getLabel("newLabel");

            var charName = ANIM_NAMES.get(activeAnimation.getName());
            newLabel.setText(String.format("%s created!", charName));
        }


        private void clearInterface(Object state) {
            var view = (UIView)state;
            var newLabel = view.getLabel("newLabel");
            newLabel.setText("Characters were cleared.");
        }

        private void switchCharacterRight(Object state) {
            var view = (UIView)state;

            List<UIAnimation> anim = view.getComponents("characterAnimation", UIAnimation.class);
            for (int i = 0; i < anim.size(); i++) {
                UIAnimation curr = anim.get(i);

                if (!curr.isEnabled()) {
                    continue;
                }

                curr.hide();

                UIAnimation next = (i+1 < anim.size()) ? anim.get(i+1) : anim.get(0);
                next.show();

                var charNameLabel = view.getLabel("characterNameLabel");
                var charName = ANIM_NAMES.get(next.getName());
                charNameLabel.setText(charName);

                break;
            }
        }

        private void switchCharacterLeft(Object state) {
            var view = (UIView)state;

            List<UIAnimation> anim = view.getComponents("characterAnimation", UIAnimation.class);
            for (int i = 0; i < anim.size(); i++) {
                UIAnimation curr = anim.get(i);

                if (!curr.isEnabled()) {
                    continue;
                }

                curr.hide();

                UIAnimation next = (i-1 >= 0) ? anim.get(i-1) : anim.get(anim.size()-1);
                next.show();

                var charNameLabel = view.getLabel("characterNameLabel");
                var charName = ANIM_NAMES.get(next.getName());
                charNameLabel.setText(charName);

                break;
            }
        }
    }
}
