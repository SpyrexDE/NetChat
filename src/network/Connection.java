package network;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    private Socket sock;
    
    private PrintWriter out; // Server -> Client
    private BufferedReader in; // Client -> Server

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
        this.out.println(s);
    }

    public String readRawLine() {
        try {
            return this.in.readLine();
        } catch (Exception e) {
            return("END");
        }
    }
}
