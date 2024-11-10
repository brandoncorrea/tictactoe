package com.brandoncorrea.ttt.server.jetty;

import com.brandoncorrea.ttt.server.AbstractServer;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class JettyServerTest {
    @Test
    public void isaJettyServer() {
        var server = new JettyServer(8080);
        assertInstanceOf(Server.class, server);
    }

    @Test
    public void isaAbstractServer() {
        var server = new JettyServer(8080);
        assertInstanceOf(AbstractServer.class, server);
    }

    @Test
    public void passesPortThrough() {
        assertEquals(8080, new JettyServer(8080).getPort());
        assertEquals(9090, new JettyServer(9090).getPort());
    }
}
