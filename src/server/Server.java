package server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Socket> clientSockets;

    private boolean running;

    private Thread acceptThread;
    private Thread mainThread;

    
    public Server(String host, int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        this.acceptThread = new Thread(() -> {this.acceptLoop();});
        this.mainThread = new Thread(() -> {this.mainLoop();});
        this.acceptThread.start();
        this.mainThread.start();
    }

    public void stop() {
        try {
            this.acceptThread.join();
            this.mainThread.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void acceptLoop() {
        while (this.running) {
            try {
                Socket clientSocket = this.serverSocket.accept();
                clientSockets.add(clientSocket);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void mainLoop() {
        while (this.running) {
            //TODO
        }
    }
}