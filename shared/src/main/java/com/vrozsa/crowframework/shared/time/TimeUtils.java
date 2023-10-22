package com.vrozsa.crowframework.shared.time;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Time related utilities.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeUtils {
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

    public static String getFormattedDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS"));
    }
}
