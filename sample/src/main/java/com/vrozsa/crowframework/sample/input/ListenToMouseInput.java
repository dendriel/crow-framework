package com.vrozsa.crowframework.sample.input;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.screen.ui.components.templates.UIFontTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UILabelTemplate;
import com.vrozsa.crowframework.shared.attributes.Rect;

public class ListenToMouseInput {
    public static void main(String[] args) {
        var crowEngine = CrowEngine.create();

        var pointerHandler = crowEngine.getInputManager().getPointerHandler();

        var mouseClicked = crowEngine.getScreenManager()
                .createLabel(UILabelTemplate.builder()
                        .text("Clicked:")
                        .font(new UIFontTemplate(32))
                        .rect(Rect.of(0, 200, 800, 50))
                        .build());

        var mousePressed = crowEngine.getScreenManager()
                .createLabel(UILabelTemplate.builder()
                        .text("Pressed:")
                        .font(new UIFontTemplate(32))
                        .rect(Rect.of(0, 300, 800, 50))
                        .build());

        var mouseReleased = crowEngine.getScreenManager()
                .createLabel(UILabelTemplate.builder()
                        .text("Released:")
                        .font(new UIFontTemplate(32))
                        .rect(Rect.of(0, 400, 800, 50))
                        .build());

        pointerHandler.addPointerClickedObserver(pos -> mouseClicked.setText(String.format("Clicked: %s", pos)));
        pointerHandler.addPointerPressedObserver(pos -> mousePressed.setText(String.format("Pressed: %s", pos)));
        pointerHandler.addPointerReleasedObserver(pos -> mouseReleased.setText(String.format("Released: %s", pos)));
    }
}
