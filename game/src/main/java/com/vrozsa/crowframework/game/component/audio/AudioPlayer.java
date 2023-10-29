package com.vrozsa.crowframework.game.component.audio;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.shared.api.audio.AudioClipPlayer;

/**
 * Component to represent the audio clips player.
 *
 * <p>The idea is that every component/game-object that needs audio capability can include requires this component via
 * getComponent (provided it was added to the GO) and play audio clips.</p>
 *
 * This is a proxy component.
 */
public class AudioPlayer extends AbstractComponent implements AudioClipPlayer {
    private final AudioClipPlayer audioClipPlayer;

    /**
     * @param audioClipPlayer the engine provided audio clips player.
     */
    public AudioPlayer(final AudioClipPlayer audioClipPlayer) {
        this.audioClipPlayer = audioClipPlayer;
    }

    @Override
    public void play(String key) {
        audioClipPlayer.play(key);
    }

    @Override
    public void stop(String key) {
        audioClipPlayer.stop(key);
    }

    @Override
    public void playSync(String key) {
        audioClipPlayer.playSync(key);
    }

    @Override
    public void update() {
        // no op.
    }
}
