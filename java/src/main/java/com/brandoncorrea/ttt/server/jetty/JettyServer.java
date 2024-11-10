package com.brandoncorrea.ttt.server.jetty;

import com.brandoncorrea.ttt.server.AbstractServer;
import org.eclipse.jetty.server.AbstractNetworkConnector;
import org.eclipse.jetty.server.Server;

public class JettyServer extends Server implements AbstractServer {
    public JettyServer(int port) {
        super(port);
    }

    public int getPort() {
        var connector = (AbstractNetworkConnector) getConnectors()[0];
        return connector.getPort();
    }
}
