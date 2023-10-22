package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.shared.api.sound.SfxPlayer;

public class NullSfxPlayer implements SfxPlayer {
    @Override
    public void play(String name) {
    }

    @Override
    public void playSync(String name) {
    }
}
