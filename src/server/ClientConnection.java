package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {
    Socket sock;
    PrintWriter in;
    BufferedReader out;

    public ClientConnection(Socket sock) {
        this.sock = sock;
        try {
            this.in = new PrintWriter(this.sock.getOutputStream(), true);
            this.out = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
