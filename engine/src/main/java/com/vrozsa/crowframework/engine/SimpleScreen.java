package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.screen.AbstractScreen;
import com.vrozsa.crowframework.shared.api.screen.View;
import com.vrozsa.crowframework.shared.attributes.Color;
import com.vrozsa.crowframework.shared.attributes.Size;

/**
 * Implements a simple screen to be able to dynamically add or remove views from it.
 */
public class SimpleScreen extends AbstractScreen {
    protected SimpleScreen(String name, Size size, Color bgColor) {
        super(name, size, bgColor);
    }

    public void addView(final View view) {
        super.addView(view.getName(), view);
    }

    protected void removeView(final View view) {
        super.removeView(view.getName());
    }
}
