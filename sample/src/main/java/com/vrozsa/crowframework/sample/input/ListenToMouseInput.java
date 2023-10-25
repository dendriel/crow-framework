package com.vrozsa.crowframework.sample.input;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.shared.attributes.Rect;

public class ListenToMouseInput {
    public static void main(String[] args) {
        var crowEngine = CrowEngine.create();

        var pointerHandler = crowEngine.getInputManager().getPointerHandler();

        var mouseClicked = crowEngine.getScreenManager().addLabel("Clicked:", 32, Rect.of(0, 200, 800, 50));
        var mousePressed = crowEngine.getScreenManager().addLabel("Pressed:", 32, Rect.of(0, 300, 800, 50));
        var mouseReleased = crowEngine.getScreenManager().addLabel("Released:", 32, Rect.of(0, 400, 800, 50));

        pointerHandler.addPointerClickedObserver(pos -> mouseClicked.setText(String.format("Clicked: %s", pos)));
        pointerHandler.addPointerPressedObserver(pos -> mousePressed.setText(String.format("Pressed: %s", pos)));
        pointerHandler.addPointerReleasedObserver(pos -> mouseReleased.setText(String.format("Released: %s", pos)));
    }
}
