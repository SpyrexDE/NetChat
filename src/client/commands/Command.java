package client.commands;
public class Command
{
    public static String[] ALIASES = {};

    Command(String[] args)
    {
        if (checkArgs(args))
            run(args);
    }

    boolean checkArgs(String[] args) { return true;}

    void run(String[] args) {}
}
