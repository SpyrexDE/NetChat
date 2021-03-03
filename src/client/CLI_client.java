package client;

import utils.Console;
import java.util.Scanner;

import utils.Colors;


public class CLI_client {
    public static String PREFIX = Colors.ANSI_CYAN + "Net" + Colors.ANSI_GREEN + "Chat " + Colors.ANSI_YELLOW + "⇝  " + Colors.ANSI_RESET;
    public static String MOTD = "MOTD";


    public boolean closed = false;

    public CLI_client(){
        Scanner input = new Scanner(System.in);
        Console.clear();
        while(!closed){
            System.out.print(PREFIX);
            run(input.nextLine());
        }
        input.close();
    }

    public void run(String input){
        System.out.println(input);
    }
}