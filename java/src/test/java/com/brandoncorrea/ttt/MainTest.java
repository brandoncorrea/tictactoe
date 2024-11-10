package com.brandoncorrea.ttt;

import com.brandoncorrea.ttt.server.memory.MemoryServerProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    MemoryServerProvider provider;
    MemoryLogger logger;

    @BeforeEach
    public void beforeEach() throws Exception {
        provider = new MemoryServerProvider();
        logger = new MemoryLogger();
        Main.run(provider, logger);
    }

    @Test
    public void createsOneServer() {
        assertEquals(1, provider.getCreatedServers().size());
    }

    @Test
    public void serverStartedAndJoined() {
        var server = provider.firstServer();
        assertTrue(server.isStarted());
        assertTrue(server.isJoined());
    }

    @Test
    public void wrapsServerWithRootContext() {
        var server = provider.firstServer();
        assertInstanceOf(RootContext.class, server.getHandler());
    }

    @Test
    public void logsHttpStatus() {
        var logs = logger.getCapturedLogs();
        assertTrue(logs.contains("INFO: Starting HTTP Service\n"));
        assertTrue(logs.contains("INFO: Server started at http://localhost:8080\n"));
    }
}
