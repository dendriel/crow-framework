package com.vrozsa.crowframework.shared.logger;

import com.vrozsa.crowframework.shared.time.TimeUtils;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Gives visibility of what is happening in the system.
 * TODO: writing to target output could be an async operation.
 */
public final class LoggerService {
    private final String className;

    private LoggerService(String className) {
        this.className = className;
    }

    public static <T> LoggerService of(final Class<T> clazz) {
        return new LoggerService(clazz.getSimpleName());
    }

    public static LoggerService of(final String loggerName) {
        return new LoggerService(loggerName);
    }

    public void debug(final String template, final Object...args) {
        print("DEBUG", template, args);
    }

    public void info(final String template, final Object...args) {
        print("INFO", template, args);

    }

    public void warn(final String template, final Object...args) {
        print("WARN", template, args);
    }

    public void error(final String template, final Object...args) {
        print("ERROR", template, args);
    }

    private void print(final String logLevel, final String template, final Object...args) {
        var dateTime = TimeUtils.getFormattedDateTime();
        var message = MessageFormat.format(template, args);
        System.out.printf("%s [%s] [%s] %s %n", dateTime, logLevel, className, message);
    }

//    private static String[] toString(final Object[] args) {
//        return Arrays.stream(args).map(Object::toString).toArray(String[]::new);
//    }
}
