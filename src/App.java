import java.util.Locale;

import server.Server;
import utils.Props;

public class App {
    private static Server server;

    public static void main(String[] args) throws Exception {
        try {
            Props.init(Locale.GERMAN);
            System.out.println(Props.get("s1"));
            //new CLI_client();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //server = new Server("0.0.0.0", 1337);
    }
}
