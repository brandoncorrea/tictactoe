package com.brandoncorrea.ttt;

public interface Logger {
    void info(Object... args);
    void error(Object... args);
    void warn(Object... args);
}
