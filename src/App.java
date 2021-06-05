import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import client.CLI_client;
import client.network.ServerConnection;
import server.Server;
import utils.Crypto;
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
            client.start();
        } else if (args.length == 3 && args[0].equals("ping")) {
            KeyPair keyPair = Crypto.generateKeyPair();
            PublicKey pubKey = keyPair.getPublic();
            PrivateKey privKey = keyPair.getPrivate();
            ServerConnection conn = new ServerConnection(args[1], Integer.parseInt(args[2]));
            
            //key exchange
            conn.keyExchange(Base64.getEncoder().encodeToString(pubKey.getEncoded()));

            //action
            conn.sendEncLine("PING");

            //evaluation
            String pong = conn.readEncLine(privKey);
            System.out.println("--- + ---\n" + pong + "\n --- + ---");

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