package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.audio.AudioClipMetadata;
import com.vrozsa.crowframework.audio.AudioHandler;
import com.vrozsa.crowframework.shared.api.audio.AudioClipPlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Crow AudioManager implementation. Allows to easily preload audio files and access then via name keys.
 */
class CrowAudioManager implements AudioManager {
    private static final String DEFAULT_AUDIO_FILEPATH = "/audio";
    private final AudioHandler audioHandler;
    private final HashMap<String, AudioClipMetadata> clipMetadata;

    public CrowAudioManager() {
        this(DEFAULT_AUDIO_FILEPATH);
    }

    /**
     * @param assetsPath path in which audio files can be found.
     */
    public CrowAudioManager(final String assetsPath) {
        this.audioHandler = AudioHandler.create(this::get, String.format("%s/%s", assetsPath, DEFAULT_AUDIO_FILEPATH));
        clipMetadata = new HashMap<>();
    }

    public Optional<AudioClipMetadata> get(String name) {
        return Optional.of(clipMetadata.get(name));
    }

    @Override
    public void addAudioClipMetadata(Collection<AudioClipMetadata> metadata) {
        requireNonNull(metadata);
        metadata.forEach(data ->  clipMetadata.put(data.key(), data));
    }

    public AudioClipPlayer getPlayer() {
        return audioHandler;
    }
}
