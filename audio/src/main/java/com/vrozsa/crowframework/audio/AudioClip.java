package com.vrozsa.crowframework.audio;

import com.vrozsa.crowframework.shared.logger.LoggerService;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

import static com.vrozsa.crowframework.shared.time.TimeUtils.microToMilli;

/**
 * Handles audio playback.
 */
final class AudioClip {
    private static final LoggerService logger = LoggerService.of(AudioClip.class);
    private final AudioClipMetadata data;
    private final URL audioUrl;
    private AudioInputStream audioStream;
    private Clip clip;
    private long length;

    private AudioClip(AudioClipMetadata data, URL audioUrl) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.data = data;
        this.audioUrl = audioUrl;
        load();
    }

    public static AudioClip of(AudioClipMetadata data, URL audioUrl)
            throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        return new AudioClip(data, audioUrl);
    }

    private void load() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioStream = AudioSystem.getAudioInputStream(audioUrl);
        var info = new DataLine.Info(Clip.class, audioStream.getFormat());
        clip = (Clip)AudioSystem.getLine(info);
        length = microToMilli(clip.getMicrosecondLength());
    }

    public String getKey() {
        return data.key();
    }

    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    public void play(boolean wait) {
        try {
            if (!clip.isOpen()) {
                clip.open(audioStream);
            }
            clip.setMicrosecondPosition(0);

            if (data.loop()) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();
        }
        catch (LineUnavailableException|IOException e) {
            logger.error("Could not play audio clip! [%s]. Ex.: %s\n", data.key(), e);
        }

        if (!wait) {
            return;
        }

        try {
            Thread.sleep(length);
        }
        catch (InterruptedException e) {
            logger.warn("Failed to wait for audio clip to play! [%s]. Ex.:", data.key(), e);
        }

        // closing the clip takes too long!
//        clip.close();
    }
}
