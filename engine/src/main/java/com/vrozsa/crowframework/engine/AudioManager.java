package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.audio.AudioClipMetadata;
import com.vrozsa.crowframework.shared.api.sound.AudioClipPlayer;

import java.util.Collection;

/**
 * Handles game music and sound effects.
 */
public interface AudioManager {
    /**
     * Add audio metadata to be played later in the game.
     * @param newSfxData sfx data to be added.
     */
    void addAudioClipMetadata(Collection<AudioClipMetadata> newSfxData);

    /**
     * Gets the sound effect player.
     * @return the sound effect player instance.
     */
    AudioClipPlayer getPlayer();
}
