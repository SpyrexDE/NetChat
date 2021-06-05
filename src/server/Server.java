package server;



import java.net.ServerSocket;
import java.net.Socket;

import server.network.ClientConnection;

public class Server {
    private String host;
    private int port;

    private ServerSocket serverSocket;

    private boolean running;
    private Thread mainThread;

    
    public Server(String host, int port) {
        this.host = host;
        this.port = port;
        this.running = false;
    }

    public void start() {
        this.running = true;
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Listening on netchat://" + this.host + ":" + this.port);
        } catch(Exception e) {
            e.printStackTrace();
        }
        this.mainThread = new Thread(() -> {this.mainLoop();});
        this.mainThread.start();
        this.acceptLoop();
    }

    public void stop() {
        this.running = false;
        try {
            this.mainThread.join();
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
                
                conn.sendRawLine("PUBKEY"); //send pubkey of the server here

                String clientPubKey = conn.readRawLine(); //get pubkey of client
                
                System.out.println(clientPubKey);
            
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