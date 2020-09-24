package com.shadow.dubbo.server;


import com.shadow.dubbo.netty.NettyServer;

public class ServerBootstrap {

    private static String HOST = "127.0.0.1";

    private static int PORT = 8000;

    public static void main(String[] args) {
        NettyServer.start(HOST, PORT);
    }
}
