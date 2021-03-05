package utils;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public class Console {


    private static Kernel32 INSTANCE = null;

    public interface Kernel32 extends StdCallLibrary {
        public Pointer GetStdHandle(int nStdHandle);

        public boolean WriteConsoleW(Pointer hConsoleOutput, char[] lpBuffer,
                int nNumberOfCharsToWrite,
                IntByReference lpNumberOfCharsWritten, Pointer lpReserved);
    }

    static {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("win")) {
            INSTANCE = (Kernel32) Native
                    .loadLibrary("kernel32", Kernel32.class);
        }
    }

    public static void println(String message) {
        boolean successful = false;
        if (INSTANCE != null) {
            Pointer handle = INSTANCE.GetStdHandle(-11);
            char[] buffer = message.toCharArray();
            IntByReference lpNumberOfCharsWritten = new IntByReference();
            successful = INSTANCE.WriteConsoleW(handle, buffer, buffer.length,
                    lpNumberOfCharsWritten, null);
            if(successful){
                System.out.println();
            }
        }
        if (!successful) {
            println(message);
        }
    }




    public static void clear()
    {
        println("\033c");
    }

    public static void error(String error)
    {
        println("⚠️  " + Props.get("error_label") + ": " + Props.get(error));
    }

    public static void error(String msg, String ... args)
    {
        println("⚠️  " + Props.get("error_label") + ": " + replaceArgs(Props.get(msg), args));
    }

    public static void success(String msg)
    {
        println("✔  " + Props.get("success_label") + ": " + Props.get(msg));
    }

    public static void success(String msg, String ... args)
    {
        println("✔  " + Props.get("success_label") + ": " + replaceArgs(Props.get(msg), args));
    }

    public static void print(String message)
    {
        System.out.print(message);
    }

    public static void printmsg(String msg)
    {
        println(Props.get(msg));
    }

    public static void printmsg(String msg, String ... args)
    {
        println(replaceArgs(Props.get(msg), args));
    }


    public static String replaceArgs(String msg, String[] args) {
        for(int i = 0; i < args.length; i++)
        {
            msg = msg.toLowerCase().replaceFirst("%arg", args[i]);
        }
        return msg;
    }
}
