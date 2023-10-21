package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.audio.SfxData;
import com.vrozsa.crowframework.audio.SfxHandler;
import com.vrozsa.crowframework.audio.api.SfxDataGetter;
import com.vrozsa.crowframework.shared.api.sound.SfxPlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Crow AudioManager implementation. Allows to easily preload audio files and access then via name keys.
 */
public class CrowAudioManager implements AudioManager, SfxDataGetter {
    private static final String DEFAULT_AUDIO_FILEPATH = "/audio";
    private final SfxHandler sfxHandler;
    private HashMap<String, SfxData> sfxData;

    public CrowAudioManager() {
        this(DEFAULT_AUDIO_FILEPATH);
    }

    /**
     * @param assetsPath path in which sound files can be found.
     */
    public CrowAudioManager(final String assetsPath) {
        this.sfxHandler = new SfxHandler(this, String.format("%s/%s", assetsPath, DEFAULT_AUDIO_FILEPATH));
        sfxData = new HashMap<>();
    }

    @Override
    public Optional<SfxData> get(String name) {
        return Optional.of(sfxData.get(name));
    }

    @Override
    public void addAudio(Collection<SfxData> newSfxData) {
        Objects.requireNonNull(newSfxData);
        newSfxData.forEach(data ->  sfxData.put(data.getName(), data));
    }

    /**
     * Gets the sound effect player.
     * @return the sound effect player instance.
     */
    public SfxPlayer getPlayer() {
        return sfxHandler;
    }
}
