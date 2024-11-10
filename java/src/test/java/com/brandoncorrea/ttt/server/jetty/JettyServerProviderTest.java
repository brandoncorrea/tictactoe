package com.brandoncorrea.ttt.server.jetty;

import com.brandoncorrea.ttt.server.ServerProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class JettyServerProviderTest {

    ServerProvider provider;

    @BeforeEach
    public void beforeEach() {
        provider = new JettyServerProvider();
    }

    @Test
    public void isaJettyServer() {
        var server = provider.fromPort(1234);
        assertInstanceOf(JettyServer.class, server);
    }

    @Test
    public void passesPortThrough() {
        assertEquals(8080, provider.fromPort(8080).getPort());
        assertEquals(9090, provider.fromPort(9090).getPort());
    }
}
