package com.brandoncorrea.ttt;

import com.brandoncorrea.ttt.server.AbstractServer;

public class Http {
    public static void wrapServer(AbstractServer server) {
        server.setHandler(new RootContext("/"));
    }
}
