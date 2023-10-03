package com.vrozsa.crowframework.shared.time;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Time related utilities.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtils {
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static long getCurrentTimeInNanos() {
        return System.nanoTime();
    }

    public static long getTimePassedSince(long time) {
        return getCurrentTime() - time;
    }

    public static long getTimePassedSinceInNanos(long time) {
        return getCurrentTimeInNanos() - time;
    }
}
