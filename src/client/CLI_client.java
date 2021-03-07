package client;

import utils.Console;
import utils.Props;
import utils.Tray;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;

import org.fusesource.jansi.AnsiConsole;

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
            put(Exit.class, new String[] { "exit", "ext", "e" });
            put(EditConf.class, new String[] { "editconf", "ec" });
            put(ReloadConf.class, new String[] { "reloadconf", "rc", "rl" });
            put(SetVar.class, new String[] { "setvar", "sv", "set" });
            put(RemoveVar.class, new String[] { "removevar", "remvar", "rv" });
            put(ListVars.class, new String[] { "listvars", "lsv", "lv" });
            put(SetName.class, new String[] { "setname", "nickname", "nick", "sn" });
            put(SetAvatar.class, new String[] { "setavatar", "avatar", "sa" });
        }
    };

    public boolean closed = false;

    public CLI_client() {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        AnsiConsole.systemInstall();
        Scanner input = new Scanner(System.in);
        try{
            new Tray("NetChat", "NetChat was successfully started!", "/resources/icon.png"); //Image not working :(
        } catch(Exception e) {
            e.printStackTrace();
        }
        Console.clear();
        Console.print(MOTD);
        while (!closed) {
            Console.print("\n" + PREFIX);

            String i = input.nextLine();

            Console.println(String.format("\033[%dA", 2)); // Move up
            Console.print("\033[2K"); // Remove margin line
            Console.print(String.format("\033[%dA", 1)); // Move up
            Console.print("\033[2K"); // Erase typed line content

            run(i);
        }
        input.close();
    }

    public void run(String input)
    {
        if (input.startsWith(Props.getConf("command_sign")))
        {
            String[] args = input.split(" ");
            String cmd = args[0].replace(Props.getConf("command_sign"), "");

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
        {
            for(String s: Props.getConfVars().keySet())
            {
                if(input.contains(Props.getConf("variable_sign") + s))
                    input = input.replace(Props.getConf("variable_sign") + s, Props.getConfVars().get(s));
            }
            Console.println(Props.getConf("avatar") + " " + Props.get("you_label") + ": " + input);
        }
    }
}