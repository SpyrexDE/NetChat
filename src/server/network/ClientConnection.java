package server.network;

import java.net.Socket;

import network.Connection;

public class ClientConnection extends Connection {
    public ClientConnection(Socket sock) {
        super(sock);
    }
}
