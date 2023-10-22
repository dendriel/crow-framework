package com.vrozsa.crowframework.sample.audio;


import com.vrozsa.crowframework.audio.AudioClipMetadata;
import com.vrozsa.crowframework.audio.AudioHandler;
import com.vrozsa.crowframework.shared.logger.LoggerService;

import java.util.Optional;

/**
 * Play an audio clip in loop (could be the background music, for example).
 */
public class PlayAndStopAudioClipLoop {
    private static final LoggerService logger = LoggerService.of(PlayAndStopAudioClipLoop.class);
    public static void main(String[] args) throws InterruptedException {
        var audioHandler = AudioHandler.create(fileName -> Optional.of(AudioClipMetadata.builder()
                .key("teleport")
                .file("teleport.wav")
                .loop(true)
                .build()),"/assets/audio/");


        // We can stop and then restart an audio clip.
        for (var i = 0; i < 3; i++) {
            logger.info("Play async...");
            audioHandler.play("teleport");

            logger.info("Wait 1 second...");
            Thread.sleep(1000);

            logger.info("Stop looping audio clip...");
            audioHandler.stop("teleport");

            logger.info("Wait 1 second...");
            Thread.sleep(1000);
        }
    }
}
