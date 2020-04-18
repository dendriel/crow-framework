package com.rozsa.crow.time;

public class Cooldown {
    private long waitingTime;
    private long startTime;
    private boolean isStarted;

    public Cooldown() {}

    public Cooldown(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void start(long waitingTime) {
        this.waitingTime = waitingTime;
        start();
    }

    public void start() {
        reset();
        isStarted = true;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isStopped() {
        return !isStarted;
    }

    private void reset() {
        startTime = TimeUtils.getCurrentTime();
    }

    public boolean isFinished() {
        return !isWaiting();
    }

    public boolean isWaiting() {
        return TimeUtils.getTimePassedSince(startTime) < waitingTime;
    }

    public void stop() {
        isStarted = false;
    }
}
