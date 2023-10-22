package com.vrozsa.crowframework.sample.audio;


import com.vrozsa.crowframework.audio.AudioClipMetadata;
import com.vrozsa.crowframework.audio.AudioHandler;

import java.util.Optional;

/**
 * Play an audio clip in loop (could be the background music, for example).
 */
public class PlayAudioClipLoop {
    public static void main(String[] args) throws InterruptedException {
        var audioHandler = AudioHandler.create(fileName -> Optional.of(AudioClipMetadata.builder()
                .key("teleport")
                .file("teleport.wav")
                .loop(true)
                .build()), "/assets/audio/");

        audioHandler.play("teleport");

        Thread.sleep(5000);
    }
}
