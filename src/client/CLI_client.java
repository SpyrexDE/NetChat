package client;

import utils.Console;
import utils.Props;
import utils.Tray;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.NoSuchElementException;

import org.fusesource.jansi.AnsiConsole;

import client.commands.*;
import utils.ClassFinder;
import utils.Colors;

public class CLI_client
{
    public static String LABEL = Colors.ANSI_CYAN + "Net" + Colors.ANSI_GREEN + "Chat" + Colors.ANSI_RESET;
    public static String PREFIX = Colors.ANSI_CYAN + "Net" + Colors.ANSI_GREEN + "Chat " + Colors.ANSI_YELLOW + "⇝  "
            + Colors.ANSI_RESET;
    public static String MOTD = Colors.ANSI_PURPLE + Props.get("welcome") + LABEL + "\n" + Props.get("motd") + "\n\n";

    public static HashMap<Class<? extends Command>, String[]> commands = new HashMap<Class<? extends Command>, String[]>() {};

    public boolean closed = false;

    public CLI_client()
    {
        try
        {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        }
        catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        AnsiConsole.systemInstall();
    }

    public void start()
    {
        Scanner input = new Scanner(System.in);
        
        try
        {
            new Tray("NetChat", "NetChat was successfully started!", "/resources/icon.png");
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        Console.clear();
        
        Console.print(MOTD);
        
        initializeCommands();


        while (!closed)
        {
            Console.print("\n" + PREFIX);
            String i = "";

            try
            {
                i = input.nextLine();
            }
            catch (NoSuchElementException e)
            {
                closed = true;
                break;
            }
            
            if (i != "")
            {
                Console.println(String.format("\033[%dA", 2)); // Move up
                Console.print("\033[2K"); // Remove margin line
                Console.print(String.format("\033[%dA", 1)); // Move up
                Console.print("\033[2K"); // Erase typed line content
                run(i);
            }
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

    void initializeCommands()
    {
        try
        {
            for(String command_name : ClassFinder.getClassNamesFromPackage("client.commands"))
            {
                Class<?> c = Class.forName("client.commands." + command_name);
                c.getDeclaredField("ALIASES").setAccessible(true);
                commands.put((Class<? extends Command>) c, (String[]) c.getDeclaredField("ALIASES").get(this));
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}