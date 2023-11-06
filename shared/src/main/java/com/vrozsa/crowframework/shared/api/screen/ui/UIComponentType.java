package com.vrozsa.crowframework.shared.api.screen.ui;

/**
 * Available User Interface (UI) component types.
 */
public enum UIComponentType {
    /**
     * Animations are dynamic images based on spritesheets.
     */
    ANIMATION,
    /**
     * Buttons are used as an action input by mouse clicking.
     */
    BUTTON,
    /**
     * Icon is any kind of static image we want to display.
     */
    ICON,
    /**
     * Input fields can be used by the user to input text data.
     */
    INPUT_FIELD,
    /**
     * Fill bars allow to visually display 'how much' of something is done or is missing (e.g.: like a health-bar).
     */
    FILL_BAR,
    /**
     * Label is a text that can be static or dynamic.
     */
    LABEL,
    LABEL_GROUP,
    SELECT,
    SLOT,
    /**
     *
     */
    SLOT_GROUP,
    SCROLL_PANE,
    BUTTON_GROUP,
}
