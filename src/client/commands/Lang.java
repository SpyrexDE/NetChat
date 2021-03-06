package client.commands;

import java.util.Locale;

import utils.Console;
import utils.Props;

public class Lang extends Command
{

    public Lang(String[] args)
    {
        super(args);
    }

    @Override
    boolean checkArgs(String[] args)
    {
        if(args.length < 2)
        {
            Console.error("err_cmd_lang_usage");
            return false;
        }

        if(!isValidLocale(args[1]))
        {
            Console.error("err_cmd_lang_not_recognizable");
            return false;
        }
        
        return true;
    }

    @Override
    void run(String[] args)
    {
        if(Props.setLang(args[1]))
        {
            Console.success("suc_cmd_lang", args[1].toUpperCase());
        }
    }

    boolean isValidLocale(String value) {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
          if (value.toLowerCase().equals(locale.toString())) {
            return true;
          }
        }
        return false;
    }
}
