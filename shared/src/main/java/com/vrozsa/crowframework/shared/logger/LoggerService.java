package com.vrozsa.crowframework.shared.logger;

public class LoggerService {
    private final String className;

    private LoggerService(String className) {
        this.className = className;
    }

    public static <T> LoggerService of(final Class<T> clazz) {
        return new LoggerService(clazz.getSimpleName());
    }

    public void info(final String template, final Object...args) {
        System.out.printf("[%s] " + template + "%n", className, args);
    }

}
