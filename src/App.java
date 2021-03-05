import java.util.Locale;

import client.CLI_client;
import server.Server;
import utils.Props;

public class App {
    private static Server server;

    public static void main(String[] args) throws Exception {
        try {
            Props.init(Locale.ENGLISH);
            new CLI_client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //server = new Server("0.0.0.0", 1337);
    }
}
