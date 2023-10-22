package com.vrozsa.crowframework.shared.api.sound;

/**
 * Allows to play audio clips.
 */
public interface AudioClipPlayer {
    /**
     * Plays an audio clip (won't block).
     * @param key the key from the audio clip to be played.
     */
    void play(String key);

    /**
     * Plays an audio clip and wait until it has finished (will block).
     * <p>
     *     WARNING: if used to play an audio clip set to loop, will block forever.
     * </p>
     * @param key the key from the audio clip to be played.
     */
    void playSync(String key);
}
