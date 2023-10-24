package com.vrozsa.crowframework.sample.input;

import com.vrozsa.crowframework.engine.CrowEngine;
import com.vrozsa.crowframework.shared.attributes.Rect;

public class TrackMousePointer {
    public static void main(String[] args) {
        var crowEngine = CrowEngine.create();

        var pointerHandler = crowEngine.getInputManager().getPointerHandler();

        var pointerAbs = crowEngine.getScreenManager().addLabel("", 32, Rect.of(0, 200, 800, 50));
        var pointerRel = crowEngine.getScreenManager().addLabel("", 32, Rect.of(0, 300, 800, 50));

        do {
            var relPos = pointerHandler.getPointerPosition();
            var absPos = pointerHandler.getPointerAbsolutePosition();
            pointerAbs.setText(String.format("Relative: %d,%d", relPos.getX(), relPos.getY()));
            pointerRel.setText(String.format("Absolute: %d,%d", absPos.getX(), absPos.getY()));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        } while (true);
    }
}
