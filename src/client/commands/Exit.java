package client.commands;

import utils.Console;

public class Exit extends Command
{
    public static final String[] ALIASES = { "exit", "ext", "e" };

    public Exit(String[] args)
    {
        super(args);
    }

    @Override
    void run(String[] args)
    {
        Console.clear();
        System.exit(0);
    }
    
}
