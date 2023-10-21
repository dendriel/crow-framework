package com.vrozsa.crowframework.game.component.audio;

import com.vrozsa.crowframework.game.component.AbstractComponent;
import com.vrozsa.crowframework.shared.api.sound.SfxPlayer;

/**
 * Component to represent the audio-engine.
 * <p>The idea is that every component/game-object that needs audio capability can include requires this component via
 * getComponent (provided it was added to the GO) and play audio files.</p>
 */
public class AudioPlayer extends AbstractComponent implements SfxPlayer {
    private final SfxPlayer sfxPlayer;

    /**
     * @param sfxPlayer the engine capable sound effects player.
     */
    public AudioPlayer(final SfxPlayer sfxPlayer) {
        this.sfxPlayer = sfxPlayer;
    }

    @Override
    public void play(String name) {
        sfxPlayer.play(name);
    }

    @Override
    public void playSync(String name) {
        sfxPlayer.playSync(name);
    }
}
