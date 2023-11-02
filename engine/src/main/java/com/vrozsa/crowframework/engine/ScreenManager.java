package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.screen.views.RendererView;
import com.vrozsa.crowframework.screen.ui.components.UIIcon;
import com.vrozsa.crowframework.screen.ui.components.UILabel;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.attributes.Rect;

/**
 * Manages screen related features.
 */
public interface ScreenManager {

    /**
     * Start rendering a Game Object. The game object must have been created with a Renderer.
     * @param go the game object to be rendered.
     */
    void renderGO(final GameObject go);

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


    /**
     * Adds a new Label component to the default screen and view.
     * @param text label initial text.
     * @param size font size.
     * @param rect label rect (defines offset and length).
     * @return the new label component.
     */
    UILabel addLabel(final String text, final int size, final Rect rect);
    /**
     * Gets the renderer view from the screen (the view which render game objects).
     * @return the renderer view, if any.
     */
    RendererView getRendererView();
}
