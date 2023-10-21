package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.audio.api.SfxDataGetter;
import com.vrozsa.crowframework.audio.api.SfxPlayer;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class SfxHandler implements SfxPlayer {
    private static final LoggerService logger = LoggerService.of(SfxHandler.class);
    private final SfxDataGetter sfxDataGetter;
    private final String assetsPath;
    private final Set<Sfx> cachedSfx; // TODO: make a hashMap

    public SfxHandler(SfxDataGetter sfxDataGetter, String assetsPath) {
        this.sfxDataGetter = sfxDataGetter;
        cachedSfx = new HashSet<>();
        this.assetsPath = assetsPath;
    }

    public void playSync(String name) {
        var optSfx = getOrCreateVfx(name);
        optSfx.ifPresent(sfx -> sfx.play(true));
    }

    public void play(String name) {
        var optSfx = getOrCreateVfx(name);
        optSfx.ifPresent(sfx -> sfx.play(false));
    }

    private Optional<Sfx> getOrCreateVfx(String name) {
        // TODO: retest this problem
        // replaying clips doesn't work on ubuntu right now. Must find a workaround.
        // If we get this code to work again, we must check if the sfx is playing.
        var sfx = cachedSfx.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (sfx != null) {
            return Optional.of(sfx);
        }

        var optSfx = createSfx(name);
        optSfx.ifPresent(cachedSfx::add);

        return optSfx;
    }

    private Optional<Sfx> createSfx(String name) {
        var sfxData = sfxDataGetter.get(name);
        if (sfxData.isEmpty()) {
            logger.error("Sound named ''{0}'' doesn't exist!", name);
            return Optional.empty();
        }

        var soundPath = formatFilePath(sfxData.get());
        var soundUrl = SfxHandler.class.getResource(soundPath);

        try {
            return Optional.of(Sfx.of(sfxData.get(), soundUrl));
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            logger.error("Failed to create SFX from file [%s]. Ex.: %s", name, e);
            return Optional.empty();
        }
    }

    private String formatFilePath(SfxData data) {
        return String.format("%s/%s", assetsPath, data.getSoundFile());
    }
}
