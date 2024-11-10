package com.brandoncorrea.ttt.server;

public interface ServerProvider {
    AbstractServer fromPort(int port);
}
