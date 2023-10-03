package com.vrozsa.crowframework.shared.logger;

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
        System.out.printf("[DEBUG] [%s] " + template + "%n", className, args);
    }

    public void info(final String template, final Object...args) {
        System.out.printf("[INFO] [%s] " + template + "%n", className, args);
    }

}
