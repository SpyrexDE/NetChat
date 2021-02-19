package server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Socket, PrintWriter, BufferedReader> clientSockets;

    private boolean running;

    private Thread acceptThread;
    private Thread mainThread;

    
    public Server(String host, int port) {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        this.acceptThread = new Thread(new Runnable(this.acceptLoop));
        this.mainThread = new Thread(new Runnable(this.mainLoop));
    }

    public void stop() {
        this.acceptThread.join();
        this.mainThread.join();
    }

    private void acceptLoop() {
        while (this.running) {
            Socket clientSocket = this.serverSocket.accept();
            clientSockets.add({clientSocket, clientSocket.getInputStream(), clientSocket.getOutputStream());
        }
    }

    private void mainLoop() {
        while (this.running) {
            //TODO
        }
    }
}