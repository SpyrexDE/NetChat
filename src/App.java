import client.GUI_client;
import server.Server;

public class App {
    
    private static GUI_client gui;
    private static Server server;

    public static void main(String[] args) throws Exception {
        try {
            gui = new GUI_client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        server = new Server("0.0.0.0", 1337);
    }
}
