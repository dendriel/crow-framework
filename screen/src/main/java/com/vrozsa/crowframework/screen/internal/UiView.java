package com.vrozsa.crowframework.screen.internal;

import com.vrozsa.crowframework.shared.attributes.Rect;

/**
 * Base view for rendering UI components.
 */
public class UiView extends AbstractView {
    /**
     * Default UI View name if no name is specified.
     */
    public static final String DEFAULT_UI_VIEW = "_defaultUiView";

    /**
     * @param rect view rect in the screen.
     */
    public UiView(Rect rect) {
        super(DEFAULT_UI_VIEW, rect);
    }

    /**
     * @param name view name so it can be referenced while accessing it from the screen.
     * @param rect view rect in the screen.
     */
    public UiView(String name, Rect rect) {
        super(name, rect);
    }

    /**
     * @param template create the view from a template.
     */
    public UiView(ViewTemplate template) {
        super(template);
    }
}
