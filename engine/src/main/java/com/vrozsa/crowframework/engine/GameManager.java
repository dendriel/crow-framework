package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.shared.api.game.GameObject;

/**
 * Manages gameplay related features.
 */
public interface GameManager {

    /**
     * Adds a GameObject into the game.
     * @param go the game object to be added.
     */
    void addGameObject(final GameObject go);
}
