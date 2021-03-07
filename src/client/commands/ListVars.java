package client.commands;

import java.util.HashMap;

import utils.Console;
import utils.Props;

public class ListVars extends Command
{

    public ListVars(String[] args)
    {
        super(args);
    }

    @Override
    void run(String[] args)
    {
        Console.println("----Variables----");
        HashMap<String, String> entries = Props.getConfVars();
        for(String key : entries.keySet())
        {
            Console.printmsgln("cmd_listvars_item", key, entries.get(key));
        }
        Console.println("-----------------");
    }
    
}
