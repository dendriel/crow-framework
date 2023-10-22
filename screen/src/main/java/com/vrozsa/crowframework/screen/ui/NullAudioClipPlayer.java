package com.vrozsa.crowframework.screen.ui;

import com.vrozsa.crowframework.shared.api.audio.AudioClipPlayer;

public class NullAudioClipPlayer implements AudioClipPlayer {
    @Override
    public void play(String key) {
        // Null object implementation.
    }

    @Override
    public void stop(String key) {
        // Null object implementation.
    }

    @Override
    public void playSync(String key) {
        // Null object implementation.
    }
}
