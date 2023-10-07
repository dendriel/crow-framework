package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.screen.ui.UIIcon;
import com.vrozsa.crowframework.shared.attributes.Rect;

/**
 * Manages screen related features.
 */
public interface ScreenManager {

    /**
     * Adds a new Icon component to the default screen and view.
     * @param imageFile icon target image.
     * @param rect icon position and size.
     * @return the new icon component.
     */
    UIIcon addIcon(final String imageFile, final Rect rect);

    /**
     * Adds a new Icon component to the default screen and view.
     * @param imageFile imageFile icon target image.
     * @param x horizontal icon offset.
     * @param y vertical icon offset.
     * @param width icon width.
     * @param height icon height.
     * @return the new icon component.
     */
    UIIcon addIcon(final String imageFile, final int x, final int y, final int width, final int height);
}
