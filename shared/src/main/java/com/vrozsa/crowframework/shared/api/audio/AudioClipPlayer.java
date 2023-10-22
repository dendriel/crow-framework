package com.vrozsa.crowframework.shared.api.audio;

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
     * Stops an ongoing audio clip playback.
     * @param key the audio clip to be stopped.
     */
    void stop(String key);

    /**
     * Plays an audio clip and wait until it has finished (will block).
     * <p>
     *     WARNING: should not be combined with a audio clip set to loop.
     * </p>
     * @param key the key from the audio clip to be played.
     */
    void playSync(String key);
}
