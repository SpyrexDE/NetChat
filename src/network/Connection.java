package network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import utils.Crypto;

public class Connection {
    private Socket sock;
    
    private PrintWriter out; // Server -> Client
    private BufferedReader in; // Client -> Server

    public PublicKey remotePubKey;

    public Connection(String host, int port) throws UnknownHostException, IOException {
        this(new Socket(host, port));
    }

    public Connection(Socket sock) {
        this.sock = sock;
        try {
            this.out = new PrintWriter(this.sock.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRawLine(String s) {
        System.out.println(">>> " + s);
        this.out.println(s);
    }

    public String readRawLine() {
        try {
            String input = this.in.readLine();
            System.out.println("<<< " + input);
            return input;
        } catch (Exception e) {
            return("END");
        }
    }

    public void sendEncLine(String s) {
        try {
            System.out.println("--- message ---\n" + s + "\n--- ("+ s.length() +") ---");
            String cipherText = Crypto.encrypt(s, this.remotePubKey);
            this.sendRawLine(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readEncLine(PrivateKey privKey) throws Exception {
        String cipherText = this.readRawLine();
        String decryptedText = Crypto.decrypt(cipherText, privKey);
        return decryptedText;
    }

    public void setRemotePubKey(String pubKeyB64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.remotePubKey = Crypto.parsePubKeyB64(pubKeyB64);
    }    
}
