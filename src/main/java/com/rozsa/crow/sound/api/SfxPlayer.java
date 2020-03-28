package com.rozsa.crow.sound.api;

public interface SfxPlayer {
    void play(String sfxName);

    /*
     * Conditional sfx play.
     */
    void play(String name, boolean playSfx);

    void playAsync(String name);
}
