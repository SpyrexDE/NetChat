package client.commands;

import utils.Console;
import utils.Props;

public class SetVar extends Command
{
    public static final String[] ALIASES = { "setvar", "sv", "set" };

    public SetVar(String[] args)
    {
        super(args);
    }

    @Override
    boolean checkArgs(String[] args)
    {
        if(args.length != 2)
        {
            Console.error("err_cmd_setvar_usage");
            return false;
        }
        return true;
    }

    @Override
    void run(String[] args)
    {
        Props.setConfVar(args[1], Console.prompt("prompt_cmd_setvar").replace(" ", "%_"));
    }
    
}
