package utils;

public class Console {
    public static void clear()
    {
        System.out.println("\033c");
    }

    public static void error(String error)
    {
        System.out.println("⚠️  " + Props.get("error_label") + ": " + Props.get(error));
    }

    public static void error(String msg, String ... args)
    {
        System.out.println("⚠️  " + Props.get("error_label") + ": " + replaceArgs(Props.get(msg), args));
    }

    public static void success(String msg)
    {
        System.out.println("✔  " + Props.get("success_label") + ": " + Props.get(msg));
    }

    public static void success(String msg, String ... args)
    {
        System.out.println("✔  " + Props.get("success_label") + ": " + replaceArgs(Props.get(msg), args));
    }

    public static void print(String message)
    {
        System.out.print(message);
    }

    public static void println(String message)
    {
        System.out.println(message);
    }

    public static void printmsg(String msg)
    {
        System.out.println(Props.get(msg));
    }

    public static void printmsg(String msg, String ... args)
    {
        System.out.println(replaceArgs(Props.get(msg), args));
    }


    public static String replaceArgs(String msg, String[] args) {
        for(int i = 0; i < args.length; i++)
        {
            msg = msg.toLowerCase().replaceFirst("%arg", args[i]);
        }
        return msg;
    }
}
