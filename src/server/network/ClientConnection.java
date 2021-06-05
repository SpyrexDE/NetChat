package server.network;

import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import network.Connection;
import server.data.Session;

public class ClientConnection extends Connection {
    public Session session; 
    
    public ClientConnection(Socket sock) {
        super(sock);
    }

    public void keyExchange(String pubKeyB64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.sendRawLine(pubKeyB64);
        String serverPubKey = this.readRawLine();
        this.setRemotePubKey(serverPubKey);
    }
}
