package client.network;

import java.net.Socket;

import network.Connection;

public class ServerConnection extends Connection {
    public ServerConnection(Socket sock) {
        super(sock);
    }
}
