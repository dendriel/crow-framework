package com.vrozsa.crowframework.shared.api.game;

/**
 * Handlers the game loop.
 */
public interface GameLoop {

    /**
     * Starts the game loop.
     */
    void start();

    /**
     * Terminates the game loop.
     * @param timeToWait grace time to wait for the game to terminate (should be at max 1 frame time).
     */
    void terminate(final long timeToWait);

    /**
     * Add an update listener to the game loop. Updates occurs ate the beginning of each game loop iteration.
     * @param listener the listener to be added.
     */
    void addUpdateListener(UpdateListener listener);

    /**
     * Remove an update listener to the game loop.
     * @param listener the listener to be removed.
     */
    void removeUpdateListener(UpdateListener listener);

    /**
     * Add a late update listener to the game loop. Late updates occurs after the update phase.
     * @param listener the listener to be added.
     */
    void addLateUpdateListener(final UpdateListener listener);

    /**
     * Remove a late update listener to the game loop.
     * @param listener the listener to be added.
     */
    void removeLateUpdateListener(final UpdateListener listener);

    /**
     * Setup explicitly the screen update listener, so it can be executed in the correct order. Screen updates occurs
     * after the late updates.
     * @param listener screen's update listener.
     */
    void setScreenUpdateListener(UpdateListener listener);
}
