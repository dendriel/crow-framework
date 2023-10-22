package com.vrozsa.crowframework.shared.api.sound;


/**
 * Allows to play sound effects.
 */
public interface SfxPlayer {
    /**
     * Plays a sound effect.
     * @param name the sound effect to be played.
     */
    void play(String name);

    /**
     * Plays a sound effect and wait until it has finished (will block).
     * @param name the sound effect to be played.
     */
    void playSync(String name);
}
