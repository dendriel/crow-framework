package com.rozsa.crow.sound;

import com.rozsa.crow.game.api.Identifiable;

public class SfxData implements Identifiable {
    private long id;
    private String name;
    private String soundFile;
    private int length;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoundFile() {
        return soundFile;
    }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
