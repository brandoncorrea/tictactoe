package com.brandoncorrea.ttt;

import com.brandoncorrea.ttt.server.ServerProvider;
import com.brandoncorrea.ttt.server.jetty.JettyServerProvider;

public class Main {
    static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        run(new JettyServerProvider(), new ConsoleLogger());
    }

    public static void run(
            ServerProvider serverProvider,
            Logger log) throws Exception {
        log.info("Starting HTTP Service");
        var server = serverProvider.fromPort(PORT);
        Http.wrapServer(server);
        server.start();
        log.info(String.format("Server started at http://localhost:%d", PORT));
        server.join();
    }
}
