package server;

import java.net.*;
import java.util.*;

public class Server {
    private String host;
    private int port;

    private ServerSocket serverSocket;
    private ArrayList<ClientConnection> clientConnections;

    private boolean running;

    private Thread mainThread;

    
    public Server(String host, int port) {
        this.host = host;
        this.port = port;
        this.running = false;
        this.clientConnections = new ArrayList<ClientConnection>();
    }

    public void start() {
        this.running = true;
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Listening on " + this.host + ":" + this.port);
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
                Socket clientSocket = this.serverSocket.accept();
                clientConnections.add(new ClientConnection(clientSocket));
                System.out.println("New connection: " + clientSocket.getRemoteSocketAddress());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void mainLoop() {
        String in;
        while (this.running) {
            for (ClientConnection conn : clientConnections) {
                try {
                    in = conn.out.readLine();
                    System.out.println(in);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}