package com.vrozsa.crowframework.screen.ui;

import com.vrozsa.crowframework.shared.api.sound.AudioClipPlayer;

public class NullAudioClipPlayer implements AudioClipPlayer {
    @Override
    public void play(String key) {

    }

    @Override
    public void playSync(String key) {

    }
}
