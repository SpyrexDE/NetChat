import client.CLI_client;
import picocli.CommandLine;
import server.Server;

public class App {
    private static Server server;

    public static void main(String[] args) throws Exception {
        try {
            CommandLine.run(new CLI_client(), System.err, args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        server = new Server("0.0.0.0", 1337);
    }
}
