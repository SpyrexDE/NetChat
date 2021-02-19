import client.GUI_client;
import server.Server;

public class App {
    
    private static GUI_client gui;
    private static Server server;

    public static void main(String[] args) throws Exception {
        gui = new GUI_client();
        server = new Server("0.0.0.0", 1337);
    }
}
