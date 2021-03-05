package client.commands;

import utils.Console;

public class Help extends Command
{

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