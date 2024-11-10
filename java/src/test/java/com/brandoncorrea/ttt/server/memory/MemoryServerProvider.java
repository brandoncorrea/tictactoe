package com.brandoncorrea.ttt.server.memory;

import com.brandoncorrea.ttt.server.AbstractServer;
import com.brandoncorrea.ttt.server.ServerProvider;

import java.util.LinkedList;
import java.util.List;

public class MemoryServerProvider implements ServerProvider {

    List<MemoryServer> _servers = new LinkedList<>();

    public AbstractServer fromPort(int port) {
        var server = new MemoryServer(port);
        _servers.add(server);
        return server;
    }

    public List<MemoryServer> getCreatedServers() {
        return _servers;
    }

    public MemoryServer firstServer() {
        return getCreatedServers().get(0);
    }
}
