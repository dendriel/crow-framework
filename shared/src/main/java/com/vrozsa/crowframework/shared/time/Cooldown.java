package com.vrozsa.crowframework.shared.time;

/**
 * Used to keep track of a passing amount time.
 */
public class Cooldown {
    private long waitingTime;
    private long startTime;
    private boolean isStarted;

    private Cooldown(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * Create a new cooldown object.
     * @param waitingTime cooldown control time in milliseconds.
     * @return the new cooldown (stopped state).
     */
    public static Cooldown create(final long waitingTime) {
        return new Cooldown(waitingTime);
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
        return !isStarted || !isWaiting();
    }

    public boolean isWaiting() {
        return TimeUtils.getTimePassedSince(startTime) < waitingTime;
    }

    public void stop() {
        isStarted = false;
    }
}
