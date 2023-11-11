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
public final class AudioPlayer extends AbstractComponent implements AudioClipPlayer {
    private final AudioClipPlayer clipPlayer;

    /**
     * @param clipPlayer the engine provided audio clips player.
     */
    private AudioPlayer(final AudioClipPlayer clipPlayer) {
        this.clipPlayer = clipPlayer;
    }

    public static AudioPlayer create(final AudioClipPlayer clipPlayer) {
        return new AudioPlayer(clipPlayer);
    }

    @Override
    public void play(String key) {
        clipPlayer.play(key);
    }

    @Override
    public void stop(String key) {
        clipPlayer.stop(key);
    }

    @Override
    public void playSync(String key) {
        clipPlayer.playSync(key);
    }

    @Override
    public void update() {
        // no op.
    }
}
