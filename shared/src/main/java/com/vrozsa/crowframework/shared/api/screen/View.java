package com.vrozsa.crowframework.shared.api.screen;

import com.vrozsa.crowframework.shared.attributes.Size;

/**
 * Views are areas of display inside a Screen.
 * A view can be used to render a whole game, or we can have multiple views for HUD, map rendering, menu, etc.
 */
public interface View {

    /**
     * Refresh the view visual elements.
     * Allows reflecting visual changes on the screen.
     * Generally, this method is called automatically.
     */
    void draw();

    /**
     * Resize the view and its components accordingly with the parent size.
     * That means the resize is relative to the size of the parent.
     * @param parentSize parent size to be used as a reference in the resizing.
     */
    void resize(final Size parentSize);

    /**
     * View name (unique identifier).
     * @return the view name.
     */
    String getName();
}
