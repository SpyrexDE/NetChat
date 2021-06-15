package client.commands;

import utils.Console;

public class Help extends Command
{
    public static final String[] ALIASES = { "help", "h" };

    public Help(String[] args)
    {
        super(args);
    }

    @Override
    void run(String[] args)
    {
        Console.printmsg("cmd_help");//args[1]); for specific command help
    }
    
}