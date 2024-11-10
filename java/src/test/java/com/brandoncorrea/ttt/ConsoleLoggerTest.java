package com.brandoncorrea.ttt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleLoggerTest {

    Logger logger;
    PrintStream originalOut;
    ByteArrayOutputStream outputStream;

    @BeforeEach
    public void beforeEach() {
        outputStream = new ByteArrayOutputStream();
        logger = new ConsoleLogger();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void afterEach() {
        System.setOut(originalOut);
    }

    @Nested
    class Info {

        @Test
        public void emptyLine() {
            logger.info();
            assertEquals("INFO: \n", capturedLogs());
        }

        @Test
        public void logsOneString() {
            logger.info("Foo");
            assertEquals("INFO: Foo\n", capturedLogs());
        }

        @Test
        public void logsTwoStrings() {
            logger.info("Foo", "Bar");
            assertEquals("INFO: Foo Bar\n", capturedLogs());
        }

        @Test
        public void oneNullStringArgument() {
            logger.info((String) null);
            assertEquals("INFO: null\n", capturedLogs());
        }

        @Test
        public void twoNullArguments() {
            logger.info(null, null);
            assertEquals("INFO: null null\n", capturedLogs());
        }

        @Test
        public void oneNullObjectArrayArgument() {
            logger.info((Object[]) null);
            assertEquals("INFO: null\n", capturedLogs());
        }

        @Test
        public void objectsCoercedToStrings() {
            var obj = new Object();
            logger.info(obj);
            assertEquals(String.format("INFO: %s\n", obj), capturedLogs());
        }
    }

    @Nested
    class Warn {

        @Test
        public void emptyLine() {
            logger.warn();
            assertEquals("WARN: \n", capturedLogs());
        }

        @Test
        public void logsOneString() {
            logger.warn("Foo");
            assertEquals("WARN: Foo\n", capturedLogs());
        }

        @Test
        public void logsTwoStrings() {
            logger.warn("Foo", "Bar");
            assertEquals("WARN: Foo Bar\n", capturedLogs());
        }
    }

    @Nested
    class Error {

        @Test
        public void emptyLine() {
            logger.error();
            assertEquals("ERROR: \n", capturedLogs());
        }

        @Test
        public void logsOneString() {
            logger.error("Foo");
            assertEquals("ERROR: Foo\n", capturedLogs());
        }

        @Test
        public void logsTwoStrings() {
            logger.error("Foo", "Bar");
            assertEquals("ERROR: Foo Bar\n", capturedLogs());
        }
    }

    String capturedLogs() {
        return outputStream.toString();
    }
}
