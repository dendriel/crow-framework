package com.rozsa.shared.api.sound;

public interface SfxPlayer {
    void play(String sfxName);

    /*
     * Conditional sfx play.
     */
    void play(String name, boolean playSfx);

    void playAsync(String name);
}
