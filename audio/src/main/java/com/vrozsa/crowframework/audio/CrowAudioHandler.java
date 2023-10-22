package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.shared.logger.LoggerService;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.isNull;

final class CrowAudioHandler implements AudioHandler {
    private static final LoggerService logger = LoggerService.of(CrowAudioHandler.class);
    private final Function<String, Optional<AudioClipMetadata>> audioClipMetadataGetter;
    private final String assetsPath;
    private final Set<AudioClip> cachedAudioClips; // TODO: make a hashMap

    private CrowAudioHandler(Function<String, Optional<AudioClipMetadata>> AudioClipMetadataGetter, String assetsPath) {
        this.audioClipMetadataGetter = AudioClipMetadataGetter;
        cachedAudioClips = new HashSet<>();
        this.assetsPath = assetsPath;
    }

    static AudioHandler create(Function<String, Optional<AudioClipMetadata>> sfxDataGetter, String assetsPath) {
        return new CrowAudioHandler(sfxDataGetter, assetsPath);
    }

    public void playSync(String key) {
        var optSfx = getOrCreateAudioClip(key);
        optSfx.ifPresent(audioClip -> audioClip.play(true));
    }

    public void play(String key) {
        var optSfx = getOrCreateAudioClip(key);
        optSfx.ifPresent(audioClip -> audioClip.play(false));
    }

    private Optional<AudioClip> getOrCreateAudioClip(String key) {
        // TODO: retest this problem
        // replaying clips doesn't work on ubuntu right now. Must find a workaround.
        // If we get this code to work again, we must check if the sfx is playing.
        var sfx = cachedAudioClips.stream()
                .filter(s -> s.getKey().equals(key))
                .findFirst()
                .orElse(null);

        if (sfx != null) {
            return Optional.of(sfx);
        }

        var optSfx = createAudioClip(key);
        optSfx.ifPresent(cachedAudioClips::add);

        return optSfx;
    }

    private Optional<AudioClip> createAudioClip(String key) {
        var audioClipMetadata = audioClipMetadataGetter.apply(key);
        if (audioClipMetadata.isEmpty()) {
            logger.error("Sound named ''{0}'' doesn't exist!", key);
            return Optional.empty();
        }

        var soundPath = formatFilePath(audioClipMetadata.get());
        var soundUrl = CrowAudioHandler.class.getResource(soundPath);
        if (isNull(soundUrl)) {
            logger.error("Sound ''{0}'' could not be found in resources.", soundPath);
        }

        try {
            return Optional.of(AudioClip.of(audioClipMetadata.get(), soundUrl));
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            logger.error("Failed to create SFX from file [%s]. Ex.: %s", key, e);
            return Optional.empty();
        }
    }

    private String formatFilePath(AudioClipMetadata data) {
        return String.format("%s/%s", assetsPath, data.file());
    }
}
