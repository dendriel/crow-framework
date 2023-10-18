package com.vrozsa.crowframework.shared.api.game;

/**
 * Adds a game object to the game-loop, also rendering the object and its children.
 */
public interface GameLoopAdder {
    /**
     * Adds a game object and its children in the game-loop.
     * @param gameObject the game object to be added.
     */
    void addGameObject(GameObject gameObject);
}
