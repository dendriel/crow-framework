package com.vrozsa.crowframework.sample.audio;


import com.vrozsa.crowframework.audio.SfxData;
import com.vrozsa.crowframework.audio.SfxHandler;
import com.vrozsa.crowframework.audio.api.SfxDataGetter;

/**
 * Simple play SFX sample.
 */
public class PlaySfx {
    public static void main(String[] args) {
        var sfxHandler = new SfxHandler(new SfxDataGetter() {
            @Override
            public SfxData getSfxData(String fileName) {
                return SfxData.builder()
                        .id(0)
                        .name("teleport")
                        .length(100)
                        .soundFile("teleport.wav")
                        .build();
            }

            @Override
            public String getSfxPath(String fileName) {
                return "/assets/sounds/" + fileName;
            }
        });

        sfxHandler.play("teleport");
    }
}
