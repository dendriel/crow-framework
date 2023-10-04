package com.vrozsa.crowframework.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Sfx {
    private final SfxData data;

    private final URL soundUrl;

    private AudioInputStream audioStream;

    private Clip clip;

    public Sfx(SfxData data, URL soundUrl) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.data = data;
        this.soundUrl = soundUrl;
        load();
    }

    private void load() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioStream = AudioSystem.getAudioInputStream(soundUrl);
        DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
        clip = (Clip)AudioSystem.getLine(info);
    }

    public String getName() {
        return data.getName();
    }

    public void play(boolean wait) {
        try {
            if (!clip.isOpen()) {
                clip.open(audioStream);
            }
            clip.setMicrosecondPosition(0);
            clip.start();
        } catch (Exception e) {
            System.out.printf("Could not play audio clip! [%s]. Ex.: %s\n", data.getName(), e);
        }

        if (!wait) {
            return;
        }

        try {
            Thread.sleep(data.getLength());
        } catch (InterruptedException e) {
            System.out.printf("Failed to wait for audio clip to play! [%s]. Ex.:", data.getName(), e);
        }

        // closing the clip takes too long!
        //clip.close();
    }
}
