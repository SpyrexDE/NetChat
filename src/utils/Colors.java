package utils;

import java.util.HashMap;

public class Colors
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String[] COLORS =
    {
        ANSI_RESET,
        ANSI_BLACK,
        ANSI_RED,
        ANSI_GREEN,
        ANSI_YELLOW,
        ANSI_BLUE,
        ANSI_PURPLE,
        ANSI_CYAN,
        ANSI_WHITE,
    };

    public static final HashMap<String, String> COLORCODES = new HashMap<String, String>()
    {{
        put(Props.getConf("colorcode_sign") + "0", ANSI_RESET);
        put(Props.getConf("colorcode_sign") + "1", ANSI_BLACK);
        put(Props.getConf("colorcode_sign") + "2", ANSI_RED);
        put(Props.getConf("colorcode_sign") + "3", ANSI_GREEN);
        put(Props.getConf("colorcode_sign") + "4", ANSI_YELLOW);
        put(Props.getConf("colorcode_sign") + "5", ANSI_BLUE);
        put(Props.getConf("colorcode_sign") + "6", ANSI_PURPLE);
        put(Props.getConf("colorcode_sign") + "7", ANSI_CYAN);
        put(Props.getConf("colorcode_sign") + "8", ANSI_WHITE);
    }};
}
