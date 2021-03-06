import client.CLI_client;
import server.Server;
import utils.Props;

public class App {
    private static Server server;
    private static CLI_client client;

    public static void main(String[] args) throws Exception {
        if(args.length > 0 && args[0].equals("server")) {
            System.out.println("Launching Server");
            server = new Server("0.0.0.0", 1337);
            server.start();
        } else {
            Props.init();
            client = new CLI_client();
        }
    }
}