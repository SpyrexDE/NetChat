package client.commands;

import utils.Console;
import utils.Props;

public class SetAvatar extends Command
{
    public static final String[] ALIASES = { "setavatar", "avatar", "sa" };

    public SetAvatar(String[] args)
    {
        super(args);
    }

    @Override
    boolean checkArgs(String[] args)
    {
        if(args.length != 2)
        {
            Console.error("err_cmd_setavatar_usage");
            return false;
        }
        if(args[1].length() < 1 || args[1].length() > 3)
        {
            Console.error("err_cmd_setavatar_range");
            return false;
        }
        return true;
    }

    @Override
    void run(String[] args)
    {
        Props.setConf("avatar", args[1].replace(" ", "%_"));
        Console.success("suc_cmd_setavatar", args[1]);
    }
    
}
