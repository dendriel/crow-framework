package com.vrozsa.crowframework.shared.api.screen;

import com.vrozsa.crowframework.shared.attributes.Size;

/**
 * Screens contains visual elements from the game. There can be multiple screens in the game controlled by
 * the ScreenHandler.
 */
public interface Screen {

    /**
     * Gets the screen name (to be used as a key).
     * @return the screen name.
     */
    String name();

    /**
     * Refresh the screen visual elements.
     * Allows to reflect visual changes in the screen. Generally, this method is called automatically.
     */
    void draw();

    /**
     * Resize the screen and its views accordingly with the parent size.
     * That means the resize is relative to the size of the parent screen.
     * @param parentSize parent size to be used as a reference in the resizing.
     */
    void resize(final Size parentSize);

    /**
     * Updates the visibility of this screen and its views.
     * @param isVisible true=visible; false=invisible.
     */
    void setVisible(boolean isVisible);

    /**
     * Find a view by its name.
     * @param name the target view name.
     * @return the view that matches with the given name; null if not found.
     */
    View getView(String name);
}
