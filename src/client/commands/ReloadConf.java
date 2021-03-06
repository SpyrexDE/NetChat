package client.commands;

import utils.Console;
import utils.Props;

public class ReloadConf extends Command
{
    public static final String[] ALIASES = { "reloadconf", "rc", "rl" };

    public ReloadConf(String[] args)
    {
        super(args);
    }

    @Override
    void run(String[] args)
    {
        Props.init();
        Console.success("suc_cmd_reload");
    }
    
}
