package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.audio.api.SfxPlayer;

public class NullSfxPlayer implements SfxPlayer {
    @Override
    public void play(String name) {
    }

    @Override
    public void playSync(String name) {
    }
}
