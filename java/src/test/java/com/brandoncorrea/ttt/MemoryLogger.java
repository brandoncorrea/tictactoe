package com.brandoncorrea.ttt;

import java.util.ArrayList;
import java.util.List;

public class MemoryLogger implements Logger {
    List<String> _capturedLogs = new ArrayList<>();

    @Override
    public void info(Object... args) {
        captureLevel("INFO", args);
    }

    @Override
    public void error(Object... args) {
        captureLevel("ERROR", args);
    }

    @Override
    public void warn(Object... args) {
        captureLevel("WARN", args);
    }


    public List<String> getCapturedLogs() {
        return _capturedLogs;
    }

    void captureLevel(String level, Object[] args) {
        String[] argStrings = new String[args.length];
        for (int i = 0; i < args.length; i++)
            argStrings[i] = args[i].toString();
        _capturedLogs.add(String.format("%s: %s%n", level, String.join(" ", argStrings)));
    }
}
