package com.brandoncorrea.ttt.server.jetty;

import com.brandoncorrea.ttt.server.AbstractServer;
import com.brandoncorrea.ttt.server.ServerProvider;

public class JettyServerProvider implements ServerProvider {
    public AbstractServer fromPort(int port) {
        return new JettyServer(port);
    }
}
