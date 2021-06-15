package client.commands;

import utils.Console;
import utils.Props;

public class SetName extends Command
{
    public static final String[] ALIASES = { "setname", "nickname", "nick", "sn" };

    public SetName(String[] args)
    {
        super(args);
    }

    @Override
    boolean checkArgs(String[] args)
    {
        if(args.length != 2)
        {
            Console.error("err_cmd_setname_usage");
            return false;
        }
        if(args[1].length() < 3 || args[1].length() > 10)
        {
            Console.error("err_cmd_setname_range");
            return false;
        }
        return true;
    }

    @Override
    void run(String[] args)
    {
        Props.setConf("nickname", args[1].replace(" ", "%_"));
        Console.success("suc_cmd_setname", args[1]);
    }
    
}
