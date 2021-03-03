import client.CLI_client;
import server.Server;
import utils.Colors;

public class App {
    private static Server server;

    public static void main(String[] args) throws Exception {
        try {
            new CLI_client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //server = new Server("0.0.0.0", 1337);
    }
}
