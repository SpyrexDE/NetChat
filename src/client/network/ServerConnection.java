package client.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import network.Connection;

public class ServerConnection extends Connection {
    public ServerConnection(String host, int port) throws UnknownHostException, IOException {
        super(host, port);
    }
    
    public ServerConnection(Socket sock) {
        super(sock);
    }

    public void keyExchange(String pubKeyB64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String serverPubKey = this.readRawLine();
        this.setRemotePubKey(serverPubKey);
        this.sendRawLine(pubKeyB64);
    }
}
