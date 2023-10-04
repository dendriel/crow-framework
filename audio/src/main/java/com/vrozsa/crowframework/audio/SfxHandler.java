package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.audio.api.SfxDataGetter;
import com.vrozsa.crowframework.audio.api.SfxPlayer;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public final class SfxHandler implements SfxPlayer {
    private static final LoggerService logger = LoggerService.of(SfxHandler.class);

    private final SfxDataGetter sfxDataGetter;
//    private final Set<Sfx> cachedSfx;

    public SfxHandler(final SfxDataGetter sfxDataGetter) {
        this.sfxDataGetter = sfxDataGetter;
//        cachedSfx = new HashSet<>();
    }

    public void play(String name) {
        Sfx vfx = getOrCreateVfx(name);
        play(vfx, true);
    }

    @Override
    public void play(String name, boolean playSfx) {
        if (playSfx) {
            play(name);
        }
    }

    public void playAsync(String name) {
        Sfx vfx = getOrCreateVfx(name);
        play(vfx, false);
    }

    private void play(Sfx sfx, boolean wait) {
        if (sfx == null) {
            return;
        }

        sfx.play(wait);
    }

    private Sfx getOrCreateVfx(String name) {
        // TODO: replaying clips doesn't work on ubuntu right now. Must find a workaround.
        // If we get this code to work again, we must check if the sfx is playing.
//        Sfx sfx = cachedSfx.stream()
//                .filter(s -> s.getName().equals(name))
//                .findFirst()
//                .orElse(null);

        Sfx sfx = null;
        if (sfx == null) {
            sfx = createSfx(name);
        }

//        cachedSfx.add(sfx);

        return sfx;
    }

    private Sfx createSfx(String name) {
        SfxData data = sfxDataGetter.getSfxData(name);
        String soundPath = sfxDataGetter.getSfxPath(data.getSoundFile());
        URL soundUrl = SfxHandler.class.getResource(soundPath);

        try {
            return new Sfx(data, soundUrl);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            logger.error("Failed to create SFX from file [%s]. Ex.: %s", name, e);
            return null;
        }
    }
}
