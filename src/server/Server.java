package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import server.network.ClientConnection;
import utils.Crypto;

public class Server {
    private String host;
    private int port;
    
    private boolean running;

    private ServerSocket serverSocket;

    private Thread acceptThread;

    private PrivateKey privKey;
    private PublicKey pubKey;
    private String pubKeyStr;
    private String privKeyStr;

    public Server(String host, int port) throws NoSuchAlgorithmException {
        this.host = host;
        this.port = port;
        this.running = false;

        //TODO save key pair
        
        KeyPair keyPair = Crypto.generateKeyPair();
        this.pubKey  = keyPair.getPublic();
        this.privKey = keyPair.getPrivate();
        this.pubKeyStr = Base64.getEncoder().encodeToString(this.pubKey.getEncoded());
        this.privKeyStr = Base64.getEncoder().encodeToString(this.privKey.getEncoded());
    }

    public void start() throws IOException {
        this.running = true;
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("Listening on netchat://" + this.host + ":" + this.port);
        this.acceptThread = new Thread(() -> {this.acceptLoop();});
        this.acceptThread.start();
        this.mainLoop();
    }

    public void stop() {
        this.running = false;
        try {
            this.acceptThread.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void acceptLoop() {
        while (this.running) {
            try {
                Socket sock = this.serverSocket.accept();
                System.out.println("New connection: " + sock.getRemoteSocketAddress());
                ClientConnection conn = new ClientConnection(sock);
                
                // public key exchange
                //! NOT SAFE AGAINST INTERCEPTING MITM, ONLY SAFE AGAINST EAVESDROPPING

                conn.sendRawLine(this.pubKeyStr);
                String clientPubKey = conn.readRawLine();
                conn.setRemotePubKey(clientPubKey);

                // get action
                String action = conn.readEncLine(this.privKey);
                System.out.println("ACTION: " + action);

                // handle action
                if (action.equals("PING")) {
                    conn.sendEncLine("PONG");
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void mainLoop() {
        while (this.running) {
            //TODO implement main loop
        }
    }
}