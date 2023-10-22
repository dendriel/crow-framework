package com.vrozsa.crowframework.game.component.audio;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.shared.api.sound.AudioClipPlayer;

/**
 * Component to represent the audio-engine.
 * <p>The idea is that every component/game-object that needs audio capability can include requires this component via
 * getComponent (provided it was added to the GO) and play audio files.</p>
 */
public class AudioPlayer extends AbstractComponent implements AudioClipPlayer {
    private final AudioClipPlayer audioClipPlayer;

    /**
     * @param audioClipPlayer the engine capable sound effects player.
     */
    public AudioPlayer(final AudioClipPlayer audioClipPlayer) {
        this.audioClipPlayer = audioClipPlayer;
    }

    @Override
    public void play(String key) {
        audioClipPlayer.play(key);
    }

    @Override
    public void playSync(String key) {
        audioClipPlayer.playSync(key);
    }
}
