package com.rozsa.crow.sound;

import com.rozsa.crow.sound.api.SfxPlayer;

public class NullSfxPlayer implements SfxPlayer {
    @Override
    public void play(String sfxName) {
        // skip.
    }

    @Override
    public void play(String name, boolean playSfx) {

    }

    @Override
    public void playAsync(String name) {

    }
}
