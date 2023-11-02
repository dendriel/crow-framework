package com.vrozsa.crowframework.screen;

import com.vrozsa.crowframework.shared.api.game.Identifiable;
import com.vrozsa.crowframework.shared.api.input.KeysListener;
import com.vrozsa.crowframework.shared.api.input.PointerListener;
import com.vrozsa.crowframework.shared.api.screen.Screen;
import com.vrozsa.crowframework.shared.attributes.Offset;
import com.vrozsa.crowframework.shared.attributes.Size;

/**
 * The WindowHandler handles all available game screens.
 * <p>
 *     The hierarchy is as follows:
 *     - WindowHandler manages the game 'window' and contains Screens
 *     - A Screen represents a 'scene' from the game and is composed of one or more views
 *     - Views allows to render in-game objects or UI components;
 *       - In simpler games, a screen will be composed at least of a RendererView (to render game-objects) and a view
 *       to render UI components. One could add more views in a single screen. One view to render the game map, another
 *       to render the inventory (can be displayed/hidden as needed); and another one to display the character
 *       attributes.
 *     - We can add a Renderer component to a game object so a image or animation can be rendered each frame
 *     - We also can add many of the UI components so we can render a HUD or other UI related elements.
 * </p>
 */
public interface WindowHandler {
    /**
     * Creates a new WindowHandler.
     * @param config the window handler configuration.
     * @return the new window handler.
     */
    static WindowHandler create(WindowHandlerConfig config) {
        return CrowWindowHandler.create(config);
    }

    /**
     * Set up a key listener to listen for keyboard inputs.
     * <p>
     *     WARNING: must be called before {@link #initialize()}.
     * </p>
     * @param keysListener the key listener callbacks.
     */
    void setupKeysListener(KeysListener keysListener);

    /**
     * Set up a pointer listener to listen for mouse inputs.
     * @param pointerListener the pointer listener callbacks.
     */
    void setupPointerListener(PointerListener pointerListener);

    /**
     * Initializes the window handler.
     * <p>
     *     Idempotent. Won't do anything if already initialized.
     * </p>
     * <p>
     *     WARNING: if you plan to handle keyboard input, you should call {@link #setupKeysListener(KeysListener)} first.
     * </p>
     */
    void initialize();

    /**
     * Update should be hooked in the game loop so the window can update its screens each frame.
     */
    void update();

    /**
     * Adds a new screen into the window.
     * <p>
     *     The screen is {@link Identifiable} and can be referenced by its using its name from
     *     {@link Identifiable#getName()}.
     * </p>
     * <p>
     *     WARNING: only screens that inherit {@link AbstractScreen} are allowed!
     * </p>
     * @param screen the screen to be added.
     */
    void addScreen(Screen screen);

    /**
     * Removes a screen from the window
     * @param name the screen name from {@link Identifiable#getName()}.
     */
    void removeScreen(String name);

    /**
     * Gets a screen from the window.
     * @param name the screen name from {@link Identifiable#getName()}.
     * @return the screen if found; null if not found.
     */
    Screen getScreen(final String name);

    /**
     * @return the window size.
     */
    Size getSize();

    /**
     * Gets the frame size. This includes the game screen are plus the extra size from the borders.
     * For instance, a window of size 800x600 can have borders sizing 16x39; thus its frame size will be 816x639.
     * @return the frame size.
     */
    Size getFrameSize();

    /**
     * Sets the visibility of the game window.
     * @param isVisible true to show the window; false to hide the window.
     */
    void setVisible(boolean isVisible);

    /**
     * Sets the visibility of a screen in the window.
     * <p>
     *     Will automatically set the visibility of other screens to <b>false</b>.
     * </p>
     * @param name the screen name from {@link Identifiable#getName()}.
     * @param isVisible the screen visibility (true=visible; false=hidden).
     */
    void setScreenVisible(String name, boolean isVisible);

    /**
     * Gets the position of the window in the player's monitor.
     * @return the window position.
     */
    Offset getPosition();

    /**
     * Listen for window close requests.
     * <p>
     *     The listener will be invoked only if {@link WindowHandlerConfig#terminateOnWindowCloseClick()} is set to
     *     false. Otherwise, the game may shutdown before invoking the listener.
     * </p>
     * <p>
     *     Calling {@link #terminate()} after handling window close request is advised, so the game can close as requested.
     * </p>
     * @param listener the listener to be called when a window close is requested.
     */
    void addOnWindowCloseRequestListener(WindowCloseRequestListener listener);

    /**
     * Removes a window close request listener.
     * @param listener the listener to be removed.
     */
    void removeOnWindowCloseRequestListener(WindowCloseRequestListener listener);

    /**
     * Terminates the game.
     */
    void terminate();
}
