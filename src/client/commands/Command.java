package client.commands;

import client.CLI_client;

public class Command
{
    Command(String[] args)
    {
        if (checkArgs(args))
            run(args);
    }

    boolean checkArgs(String[] args) { return true;}

    void run(String[] args) {}
}
