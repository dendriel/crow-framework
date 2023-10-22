package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.shared.api.audio.AudioClipPlayer;

import java.util.Optional;
import java.util.function.Function;

/**
 * Deals with audio clips loading and execution.
 */
public interface AudioHandler extends AudioClipPlayer {
    /**
     * Creates a new AudioHandler.
     * @param audioClipMetadataGetter function to retrieve audio clip metadata.
     * @param assetsPath assets paths so audio files can be loaded. The final audio file path will be the concatenation
     *                   of the assets path + audio file name (retrieved from the audio metadata).
     * @return the new AudioHandler instance.
     */
    static AudioHandler create(Function<String, Optional<AudioClipMetadata>> audioClipMetadataGetter, String assetsPath) {
        return CrowAudioHandler.create(audioClipMetadataGetter, assetsPath);
    }

    /**
     * Gets an empty implementation of the AudioHandler.
     * <p>
     *     Can be used to remove the audio from the game without changing the code. Just inject the empty AudioHandler
     *     instance instead in the place of the real AudioHandler.
     * </p>
     * @return the empty AudioHandler instance.
     */
    static AudioHandler empty() {
        return NullAudioHandler.get();
    }
}
