package com.rozsa.crow.time;

public class Cooldown {
    private long waitingTime;
    private long startTime;
    private boolean isStarted;

    public Cooldown(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void start(long waitingTime) {
        this.waitingTime = waitingTime;
        start();
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isStopped() {
        return !isStarted;
    }

    public void start() {
        reset();
        isStarted = true;
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
