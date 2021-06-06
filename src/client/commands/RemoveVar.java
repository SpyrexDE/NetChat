package client.commands;

import utils.Console;
import utils.Props;

public class RemoveVar extends Command
{
    public static final String[] ALIASES = { "removevar", "remvar", "rv" };

    public RemoveVar(String[] args)
    {
        super(args);
    }

    @Override
    boolean checkArgs(String[] args)
    {
        if(args.length != 2)
        {
            Console.error("err_cmd_removevar_usage");
            return false;
        }
        return true;
    }

    @Override
    void run(String[] args)
    {
        if(Props.removeConfVar(args[1]))
            Console.success("suc_cmd_removevar", args[1]);
        else
            Console.error("err_cmd_removevar_not_found", args[1]);
    }
    
}
