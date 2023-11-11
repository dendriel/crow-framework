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
    private final Function<String, Optional<AudioClipMetadata>> clipMetadataGetter;
    private final String assetsPath;
    private final Set<AudioClip> cachedClips; // TODO: make a hashMap

    private CrowAudioHandler(Function<String, Optional<AudioClipMetadata>> clipMetadataGetter, String assetsPath) {
        this.clipMetadataGetter = clipMetadataGetter;
        cachedClips = new HashSet<>();
        this.assetsPath = assetsPath;
    }

    static AudioHandler create(Function<String, Optional<AudioClipMetadata>> clipMetadataGetter, String assetsPath) {
        return new CrowAudioHandler(clipMetadataGetter, assetsPath);
    }

    public void playSync(String key) {
        var optClip = getOrCreateAudioClip(key);
        optClip.ifPresent(audioClip -> audioClip.play(true));
    }

    public void play(String key) {
        var optClip = getOrCreateAudioClip(key);
        optClip.ifPresent(audioClip -> audioClip.play(false));
    }

    public void stop(String key) {
        var optClip = getOrCreateAudioClip(key);
        optClip.ifPresent(AudioClip::stop);
    }

    private Optional<AudioClip> getOrCreateAudioClip(String key) {
        // TODO: retest this problem
        // replaying clips doesn't work on ubuntu right now. Must find a workaround.
        // If we get this code to work again, we must check if the clip is playing.
        var clip = cachedClips.stream()
                .filter(s -> s.getKey().equals(key))
                .findFirst()
                .orElse(null);

        if (clip != null) {
            return Optional.of(clip);
        }

        var optClip = createAudioClip(key);
        optClip.ifPresent(cachedClips::add);

        return optClip;
    }

    private Optional<AudioClip> createAudioClip(String key) {
        var clipMetadata = clipMetadataGetter.apply(key);
        if (clipMetadata.isEmpty()) {
            logger.error("Audio clip metadata named ''{0}'' not found!", key);
            return Optional.empty();
        }

        var audioFilePath = formatFilePath(clipMetadata.get());
        var audioUrl = CrowAudioHandler.class.getResource(audioFilePath);
        if (isNull(audioUrl)) {
            logger.error("Audio file ''{0}'' could not be found in resources.", audioFilePath);
        }

        try {
            return Optional.of(AudioClip.of(clipMetadata.get(), audioUrl));
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            logger.error("Failed to create audio clip from file [%s]. Ex.: %s", key, e);
            return Optional.empty();
        }
    }

    private String formatFilePath(AudioClipMetadata data) {
        return String.format("%s/%s", assetsPath, data.file());
    }
}
