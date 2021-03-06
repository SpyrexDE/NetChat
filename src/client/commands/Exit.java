package client.commands;

import utils.Console;

public class Exit extends Command
{

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
