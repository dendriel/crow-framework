package com.vrozsa.crowframework.sample.audio;


import com.vrozsa.crowframework.audio.AudioHandler;
import com.vrozsa.crowframework.audio.AudioClipMetadata;

import java.util.Optional;

/**
 * Simple audio clip playing sample.
 */
public class PlayAudioClip {
    public static void main(String[] args) {
        var audioHandler = AudioHandler.create(fileName -> Optional.of(AudioClipMetadata.builder()
                .key("teleport")
                .file("teleport.wav")
                .build()),"/assets/sounds/");

        audioHandler.playSync("teleport");
    }
}
