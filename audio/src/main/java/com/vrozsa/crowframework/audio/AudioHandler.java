package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.shared.api.sound.AudioClipPlayer;

import java.util.Optional;
import java.util.function.Function;

/**
 * Deals with audio loading and execution.
 */
public interface AudioHandler extends AudioClipPlayer {
    /**
     * Creates a new AudioHandler.
     * @param sfxDataGetter function to retrieve audio data.
     * @param assetsPath assets paths so audio files can be loaded. The final audio file path will be the concatenation
     *                   of the assets path + audio file name (retrieved from the audio metadata).
     * @return the new AudioHandler instance.
     */
    static AudioHandler create(Function<String, Optional<AudioClipMetadata>> sfxDataGetter, String assetsPath) {
        return CrowAudioHandler.create(sfxDataGetter, assetsPath);
    }

    /**
     * Gets an empty implementation of the AudioHandler.
     * @return the empty AudioHandler instance.
     */
    static AudioHandler empty() {
        return NullAudioHandler.get();
    }
}
