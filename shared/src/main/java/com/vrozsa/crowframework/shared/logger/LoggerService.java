package com.vrozsa.crowframework.shared.logger;

import java.util.Arrays;
import java.util.List;

/**
 * Gives visibility of what is happening in the system.
 * TODO: writing to target output could be an async operation.
 */
public class LoggerService {
    private final String className;

    private LoggerService(String className) {
        this.className = className;
    }

    public static <T> LoggerService of(final Class<T> clazz) {
        return new LoggerService(clazz.getSimpleName());
    }

    public static <T> LoggerService of(final String loggerName) {
        return new LoggerService(loggerName);
    }

    public void debug(final String template, final Object...args) {
        System.out.printf("[DEBUG] [%s] " + template + "%n", className, toString(args));

    }

    public void info(final String template, final Object...args) {
        System.out.printf("[INFO] [%s] " + template + "%n", className, toString(args));
    }

    private static List<String> toString(final Object[] args) {
        return Arrays.stream(args).map(Object::toString).toList();
    }
}
