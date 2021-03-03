package client;

import utils.Console;
import utils.Tray;

import java.util.Scanner;

import utils.Colors;


public class CLI_client {
    public static String LABEL = Colors.ANSI_CYAN + "Net" + Colors.ANSI_GREEN + "Chat" + Colors.ANSI_RESET;
    public static String PREFIX = Colors.ANSI_CYAN + "Net" + Colors.ANSI_GREEN + "Chat " + Colors.ANSI_YELLOW + "⇝  " + Colors.ANSI_RESET;
    public static String MOTD = Colors.ANSI_PURPLE + "Welcome to " + LABEL + "\nType a message or run a command with \"/\"" + "\n\n";


    public boolean closed = false;

    public CLI_client(){
        Scanner input = new Scanner(System.in);
        new Tray("NetChat", "NetChat was successfully started!", "resources/icon.png"); //Image not working :(
        Console.clear();
        System.out.print(MOTD);
        while(!closed){
            System.out.print("\n" + PREFIX);

            String i = input.nextLine();
            
            
            System.out.println(String.format("\033[%dA", 2)); // Move up
            System.out.print("\033[2K");                      // Remove margin line
            System.out.print(String.format("\033[%dA", 1));   // Move up
            System.out.print("\033[2K");                      // Erase typed line content

            run(i);
        }
        input.close();
    }

    public void run(String input){
        if(input.startsWith("/"))
            System.out.println("⚠️  " + "Error" + ": " + "Command not found.");
        else if(!input.isEmpty())
            System.out.println("👦 " + "You" + ": " + input);
    }
}