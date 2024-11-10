package com.brandoncorrea.ttt.server;

import org.eclipse.jetty.server.Handler;

public interface AbstractServer {
    void join() throws InterruptedException;

    void start() throws Exception;

    void setHandler(Handler handler);

    int getPort();
}
