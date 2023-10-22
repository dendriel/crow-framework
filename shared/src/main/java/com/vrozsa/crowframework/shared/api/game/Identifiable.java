package com.vrozsa.crowframework.shared.api.game;

/**
 * Identifies an game element.
 */
public interface Identifiable {
    /**
     * Element unique identifier.
     * @return the element unique identifier.
     */
    long getId();

    /**
     * Elements name (may not be unique).
     * @return the element name.
     */
    String getName();
}
