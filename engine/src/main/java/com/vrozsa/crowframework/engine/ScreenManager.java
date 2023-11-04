package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.screen.ui.components.UIIcon;
import com.vrozsa.crowframework.screen.ui.components.UILabel;
import com.vrozsa.crowframework.screen.ui.components.button.UIButton;
import com.vrozsa.crowframework.screen.ui.components.templates.UIButtonTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UIIconTemplate;
import com.vrozsa.crowframework.screen.ui.components.templates.UILabelTemplate;
import com.vrozsa.crowframework.screen.views.RendererView;
import com.vrozsa.crowframework.shared.api.game.GameObject;
import com.vrozsa.crowframework.shared.api.screen.Screen;
import com.vrozsa.crowframework.shared.api.screen.View;
import com.vrozsa.crowframework.shared.attributes.Size;

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
     * Creates a new button into the default screen and UI view.
     * @param template the button template.
     * @return the created button.
     */
    UIButton createButton(UIButtonTemplate template);

    /**
     * Adds a new icon to the default screen and UI view.
     * @param template the icon template.
     * @return the created icon.
     */
    UIIcon createIcon(UIIconTemplate template);

    /**
     * Adds a new label to the default screen and UI view.
     * @param template the label template.
     * @return the created label.
     */
    UILabel createLabel(UILabelTemplate template);

    /**
     * Gets the renderer view from the screen (the view which render game objects).
     * @return the renderer view, if any.
     */
    RendererView getRendererView();

    /**
     * Adds a view into the default screen.
     * <p>
     *     <b>WARNING</b>: requires the window to be initialized in {@link WindowMode#DEFAULT} mode, so the default
     *     screen is present.
     * </p>
     * @param view the view to be added.
     */
    void addView(View view);

    /**
     * Adds a screen into the window.
     * @param screen the screen to be added.
     */
    void addScreen(final Screen screen);

    /**
     * Gets the window size.
     * @return the window size.
     */
    Size getSize();
}
