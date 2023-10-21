package com.vrozsa.crowframework.engine;

import com.vrozsa.crowframework.audio.SfxData;
import com.vrozsa.crowframework.audio.SfxHandler;
import com.vrozsa.crowframework.audio.api.SfxDataGetter;
import com.vrozsa.crowframework.audio.api.SfxPlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Crow AudioManager implementation. Allows to easily preload audio files and access then via name keys.
 */
public class CrowAudioManager implements AudioManager, SfxDataGetter {
    private static final String DEFAULT_AUDIO_FILEPATH = "/assets/audio";
    private final SfxHandler sfxHandler;
    private HashMap<String, SfxData> sfxData;

    public CrowAudioManager() {
        this.sfxHandler = new SfxHandler(this, DEFAULT_AUDIO_FILEPATH);
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
