import client.CLI_client;
import server.Server;
import utils.Props;

public class App {
    private static Server server;
    private static CLI_client client;

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("server")) {
            System.out.println("Launching Server");
            server = new Server("0.0.0.0", 1337);
            server.start();
        } else if (args.length > 0 && args[0].equals("client")){
            Props.init();
            client = new CLI_client();
        } else {
            //TODO add OS check here
            
            //Try to launch Windows Terminal
            Process process = Runtime.getRuntime().exec("cmd.exe /c start cmd /c \" wt new-tab -p \"Command Prompt\" -d \"%cd%\" cmd /k java -jar --enable-preview NetChat.jar client\"");
            //If could not be started -> open cmd
            if(process.waitFor() != 0)
            {
                Runtime.getRuntime().exec("cmd.exe /c start cmd /k \" java -jar --enable-preview NetChat.jar client \"");
            }
        }
    }
}