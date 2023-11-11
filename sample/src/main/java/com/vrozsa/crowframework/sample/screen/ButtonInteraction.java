package com.vrozsa.crowframework.sample.screen;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.engine.CrowEngineConfig;
import com.vrozsa.crowframework.screen.ui.UIExpandMode;
import com.vrozsa.crowframework.screen.ui.components.button.UIButton;
import com.vrozsa.crowframework.screen.ui.components.templates.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIFontTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIIconTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UILabelTemplate;
import com.vrozsa.crowframework.screen.ui.listener.UIEventListener;
import com.vrozsa.crowframework.shared.attributes.Rect;

import static com.vrozsa.crowframework.sample.TestValues.BACKGROUND_IMAGE_FILE;
import static com.vrozsa.crowframework.sample.TestValues.SCREEN_MIDDLE;

public final class ButtonInteraction {
    public static void main(String[] args) {

        var config = CrowEngineConfig.builder()
                .title("Button Interaction Sample")
                .windowResizable(true)
                .build();
        var crowEngine = CrowEngine.create(config);

        addInteractableButton(crowEngine);
        addDisabledButton(crowEngine);
        addActionableButton(crowEngine);
    }

    private static void addInteractableButton(CrowEngine crowEngine) {
        var screenManager = crowEngine.getScreenManager();

        screenManager.createIcon(UIIconTemplate.builder()
                .imageFile(BACKGROUND_IMAGE_FILE)
                .rect(Rect.atOrigin(screenManager.getSize()))
                .build());

        var buttonLabelTemplate = UILabelTemplate.builder()
                .text("Click")
                .font(new UIFontTemplate(24))
                .verticalAlignment(0)
                .horizontalAlignment(0)
                .rect(Rect.of(0, 0, 400, 50))
                .build();

        var button = screenManager.createButton(UIButtonTemplate.builder()
                .rect(Rect.of(SCREEN_MIDDLE.getX() - 100, SCREEN_MIDDLE.getY() - 25, 200, 50))
                .expandMode(UIExpandMode.FILL)
                .defaultImage("/assets/ui/button.png")
                .pressedImage("/assets/ui/button_pressed.png")
                .label(buttonLabelTemplate)
                .build());

        var displayLabelTemplate = buttonLabelTemplate.clone();
        displayLabelTemplate.setRect(Rect.of(SCREEN_MIDDLE.getX() - 200, SCREEN_MIDDLE.getY() -200, 400, 50));
        displayLabelTemplate.setText("DISPLAYER");

        var label = screenManager.createLabel(displayLabelTemplate);

        UIEventListener heldListener = s -> label.setText("Button held!");
        button.addButtonHeldListener(heldListener, null);
        UIEventListener releasedListener = s -> label.setText("Button released!");
        button.addButtonReleasedListener(releasedListener, null);

        UIEventListener onMouseEntered = s -> label.setText("Mouse entered");
        button.addMouseEnteredListener(onMouseEntered, null);
        UIEventListener onMouseExited = s -> label.setText("Mouse exited");
        button.addMouseExitedListener(onMouseExited, null);
    }

    private static void addDisabledButton(CrowEngine crowEngine) {
        var screenManager = crowEngine.getScreenManager();

        var buttonLabelTemplate = UILabelTemplate.builder()
                .text("Disabled Button")
                .font(new UIFontTemplate(24))
                .verticalAlignment(0)
                .horizontalAlignment(0)
                .rect(Rect.of(0, 0, 400, 50))
                .build();

        screenManager.createButton(UIButtonTemplate.builder()
                .rect(Rect.of(SCREEN_MIDDLE.getX() - 100, SCREEN_MIDDLE.getY() + 50, 200, 50))
                .expandMode(UIExpandMode.FILL)
                .defaultImage("/assets/ui/button.png")
                .disabledImage("/assets/ui/button_disabled.png")
                .label(buttonLabelTemplate)
                .isDisabled(true)
                .build());
    }

    private static void addActionableButton(CrowEngine crowEngine) {
        var screenManager = crowEngine.getScreenManager();

        var buttonLabelTemplate = UILabelTemplate.builder()
                .text("Click to disable")
                .font(new UIFontTemplate(24))
                .verticalAlignment(0)
                .horizontalAlignment(0)
                .rect(Rect.of(0, 0, 400, 50))
                .build();

        var button = screenManager.createButton(UIButtonTemplate.builder()
                .rect(Rect.of(SCREEN_MIDDLE.getX() - 100, SCREEN_MIDDLE.getY() + 125, 200, 50))
                .expandMode(UIExpandMode.FILL)
                .defaultImage("/assets/ui/button.png")
                .pressedImage("/assets/ui/button_pressed.png")
                .disabledImage("/assets/ui/button_disabled.png")
                .label(buttonLabelTemplate)
                .build());

        button.addButtonPressedListener(b -> ((UIButton)b).setDisabled(true), button);
    }
}
