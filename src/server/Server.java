package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import server.network.ClientConnection;
import utils.Crypto;
import utils.Props;

public class Server {
    private String host;
    private int port;
    
    private boolean running;

    private ServerSocket serverSocket;

    private Thread acceptThread;

    private PrivateKey privKey;
    private PublicKey pubKey;
    private String pubKeyB64;
    private String privKeyB64;

    public Server(String host, int port) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.host = host;
        this.port = port;
        this.running = false;

        // check for saved key pair

        privKeyB64 = Props.getConf("id_priv");
        pubKeyB64 = Props.getConf("id_pub");
        
        if(privKeyB64 == null || pubKeyB64 == null) {
            // Generate new key pair
            KeyPair keyPair = Crypto.generateKeyPair();
            this.pubKey  = keyPair.getPublic();
            this.privKey = keyPair.getPrivate();
            this.pubKeyB64 = Base64.getEncoder().encodeToString(this.pubKey.getEncoded());
            this.privKeyB64 = Base64.getEncoder().encodeToString(this.privKey.getEncoded());

            // save key pair
            Props.setConf("id_pub", pubKeyB64);
            Props.setConf("id_priv", privKeyB64);
        } else {
            // import saved key pair
            byte[] pubBytes = Base64.getDecoder().decode(pubKeyB64);
            byte[] privBytes = Base64.getDecoder().decode(privKeyB64);

            pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pubBytes));
            privKey = KeyFactory.getInstance("RSA").generatePrivate(new X509EncodedKeySpec(privBytes));
        }
    }

    public void start() throws IOException {
        
        this.running = true;
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("Listening on " + this.host + ":" + this.port);
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
                conn.keyExchange(pubKeyB64);

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