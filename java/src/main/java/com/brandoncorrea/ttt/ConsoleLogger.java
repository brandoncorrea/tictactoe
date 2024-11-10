package com.brandoncorrea.ttt;

import java.util.Arrays;

public class ConsoleLogger implements Logger {

    final String[] NULL_ARGS = new String[]{"null"};

    @Override
    public void info(Object... args) {
        printLevel("INFO", args);
    }

    @Override
    public void error(Object... args) {
        printLevel("ERROR", args);
    }

    @Override
    public void warn(Object... args) {
        printLevel("WARN", args);
    }

    void printLevel(String level, Object[] args) {
        System.out.printf("%s: %s%n", level, joinAsString(args));
    }

    String joinAsString(Object[] objects) {
        return String.join(" ", collectStrings(objects));
    }

    String[] collectStrings(Object[] objects) {
        if (objects == null)
            return NULL_ARGS;
        return Arrays
                .stream(objects)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}
