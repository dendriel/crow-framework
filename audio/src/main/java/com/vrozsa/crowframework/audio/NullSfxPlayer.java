package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.audio.api.SfxPlayer;

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
