package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.audio.AudioClipMetadata;
import com.vrozsa.crowframework.audio.AudioHandler;
import com.vrozsa.crowframework.shared.api.sound.AudioClipPlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Crow AudioManager implementation. Allows to easily preload audio files and access then via name keys.
 */
class CrowAudioManager implements AudioManager {
    private static final String DEFAULT_AUDIO_FILEPATH = "/audio";
    private final AudioHandler sfxHandler;
    private final HashMap<String, AudioClipMetadata> sfxData;

    public CrowAudioManager() {
        this(DEFAULT_AUDIO_FILEPATH);
    }

    /**
     * @param assetsPath path in which sound files can be found.
     */
    public CrowAudioManager(final String assetsPath) {
        this.sfxHandler = AudioHandler.create(this::get, String.format("%s/%s", assetsPath, DEFAULT_AUDIO_FILEPATH));
        sfxData = new HashMap<>();
    }

    public Optional<AudioClipMetadata> get(String name) {
        return Optional.of(sfxData.get(name));
    }

    @Override
    public void addAudioClipMetadata(Collection<AudioClipMetadata> metadata) {
        requireNonNull(metadata);
        metadata.forEach(data ->  sfxData.put(data.key(), data));
    }

    /**
     * Gets the sound effect player.
     * @return the sound effect player instance.
     */
    public AudioClipPlayer getPlayer() {
        return sfxHandler;
    }
}
