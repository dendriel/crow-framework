package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.audio.SfxData;
import com.vrozsa.crowframework.audio.api.SfxPlayer;

import java.util.Collection;

/**
 * Handles game music and sound effects.
 */
public interface AudioManager {
    /**
     * Add audio metadata to be played later in the game.
     * @param newSfxData sfx data to be added.
     */
    void addAudio(Collection<SfxData> newSfxData);

    /**
     * Gets the sound effect player.
     * @return the sound effect player instance.
     */
    SfxPlayer getPlayer();
}
