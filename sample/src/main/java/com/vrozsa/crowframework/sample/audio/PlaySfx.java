package com.vrozsa.crowframework.sample.audio;


import com.vrozsa.crowframework.audio.SfxData;
import com.vrozsa.crowframework.audio.SfxHandler;

import java.util.Optional;

/**
 * Simple play SFX sample.
 */
public class PlaySfx {
    public static void main(String[] args) {
        var sfxHandler = new SfxHandler(fileName -> Optional.of(SfxData.builder()
                .id(0)
                .name("teleport")
                .length(100)
                .soundFile("teleport.wav")
                .build()),"/assets/sounds/");

        sfxHandler.playSync("teleport");
    }
}
