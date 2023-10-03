package com.rozsa.screen.ui;

import com.rozsa.shared.api.sound.SfxPlayer;

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
