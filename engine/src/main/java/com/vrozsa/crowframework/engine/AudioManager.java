package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.audio.AudioClipMetadata;
import com.vrozsa.crowframework.shared.api.audio.AudioClipPlayer;

import java.util.Collection;

/**
 * Handles game music and sound effects.
 */
public interface AudioManager {
    /**
     * Add audio metadata to be played later in the game.
     * @param metadata audio clip medata to be added.
     */
    void addAudioClipMetadata(Collection<AudioClipMetadata> metadata);

    /**
     * Gets the audio clips player.
     * @return the audio clips player instance.
     */
    AudioClipPlayer getPlayer();
}
