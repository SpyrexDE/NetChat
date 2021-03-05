package client;

import utils.Console;
import utils.Props;
import utils.Tray;

import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import client.commands.*;
import utils.Colors;

public class CLI_client {
    public static String LABEL = Colors.ANSI_CYAN + "Net" + Colors.ANSI_GREEN + "Chat" + Colors.ANSI_RESET;
    public static String PREFIX = Colors.ANSI_CYAN + "Net" + Colors.ANSI_GREEN + "Chat " + Colors.ANSI_YELLOW + "⇝  "
            + Colors.ANSI_RESET;
    public static String MOTD = Colors.ANSI_PURPLE + Props.get("welcome") + LABEL + "\n" + Props.get("motd") + "\n\n";

    public static HashMap<Class<? extends Command>, String[]> commands = new HashMap<Class<? extends Command>, String[]>() {
        {
            put(Lang.class, new String[] { "language", "lang", "setlang" });
            put(Clear.class, new String[] { "clear", "c" });
            put(Help.class, new String[] { "help", "h" });
        }
    };

    public boolean closed = false;

    public CLI_client() {
        
        Scanner input = new Scanner(System.in);
        try{
            new Tray("NetChat", "NetChat was successfully started!", "resources/icon.png"); //Image not working :(
        } catch(Exception e) {
            e.printStackTrace();
        }
        Console.clear();
        System.out.print(MOTD);
        while (!closed) {
            System.out.print("\n" + PREFIX);

            String i = input.nextLine();

            System.out.println(String.format("\033[%dA", 2)); // Move up
            System.out.print("\033[2K"); // Remove margin line
            System.out.print(String.format("\033[%dA", 1)); // Move up
            System.out.print("\033[2K"); // Erase typed line content

            run(i);
        }
        input.close();
    }

    public void run(String input)
    {
        if (input.startsWith("/"))
        {
            String[] args = input.split(" ");
            String cmd = args[0].replace("/", "");

            for (Class<? extends Command> c : commands.keySet())
            {
                for (String s : commands.get(c))
                {
                    if (cmd.equals(s))
                    {
                        try
                        {
                            c.getDeclaredConstructor(String[].class).newInstance((Object)args);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            }
            Console.error("err_cmd_not_found");
        }
        else if(!input.isEmpty())
            Console.println("👦 " + Props.get("you_label") + ": " + input);
    }
}