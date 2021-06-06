package client.commands;

import utils.Props;
import utils.Console;

import java.io.File;

public class EditConf extends Command
{
    public static final String[] ALIASES = { "editconf", "ec" };

    public EditConf(String[] args)
    {
        super(args);
    }

    @Override
    void run(String[] args)
    {
        try
        {
            java.awt.Desktop.getDesktop().open(new File(Props.confPath));
            Console.success("suc_cmd_editconf");
        }
        catch (Exception e)
        {
            Console.error("err_cmd_editconf", e.getMessage());
        }
    }
    
}
