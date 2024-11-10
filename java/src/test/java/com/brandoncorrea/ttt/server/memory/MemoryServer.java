package com.brandoncorrea.ttt.server.memory;

import com.brandoncorrea.ttt.server.AbstractServer;
import org.eclipse.jetty.server.Handler;

public class MemoryServer implements AbstractServer {
    Handler _handler;
    boolean _joined = false;
    boolean _started = false;
    final int _port;

    public MemoryServer(int port) {
        _port = port;
    }

    public void join() {
        _joined = true;
    }

    public void start() {
        _started = true;
    }

    public void setHandler(Handler handler) {
        _handler = handler;
    }

    public boolean isJoined() {
        return _joined;
    }

    public boolean isStarted() {
        return _started;
    }

    public Handler getHandler() {
        return _handler;
    }

    public int getPort() {
        return _port;
    }
}
