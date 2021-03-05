package client.commands;

import utils.Console;

public class Clear extends Command
{

    public Clear(String[] args)
    {
        super(args);
    }

    @Override
    void run(String[] args)
    {
        Console.clear();
    }
    
}
