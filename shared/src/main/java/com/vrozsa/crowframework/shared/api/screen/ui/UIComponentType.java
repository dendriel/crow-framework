package com.vrozsa.crowframework.shared.api.screen.ui;

/**
 * Available User Interface (UI) component types.
 */
public enum UIComponentType {
    /**
     * Label is a text that can be static or dynamic.
     */
    LABEL,
    /**
     * Icon is any kind of static image we want to display.
     */
    ICON,
    /**
     * Input fields can be used by the user to input text data.
     */
    INPUT_FIELD,
    /**
     * Buttons are used as an action input by mouse clicking.
     */
    BUTTON,
    /**
     * Animations are dynamic images based on spritesheets.
     */
    ANIMATION,
    /**
     *
     */
    SLOT_GROUP,
    LABEL_GROUP,
    SELECT,
    SLOT,
    SLIDER,
    SCROLL_PANE,
    BUTTON_GROUP,
}
