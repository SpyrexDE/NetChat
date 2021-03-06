package utils;

import java.util.Scanner;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;


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
            INSTANCE = (Kernel32) Native.load("kernel32", Kernel32.class, com.sun.jna.win32.W32APIOptions.UNICODE_OPTIONS);
        }
    }

    public static void println(String message)
    {
        message = processColorCodes(message);
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
            System.out.println(message);
        }
    }

    public static void print(String message)
    {
        message = processColorCodes(message);
        boolean successful = false;
        if (INSTANCE != null) {
            Pointer handle = INSTANCE.GetStdHandle(-11);
            char[] buffer = message.toCharArray();
            IntByReference lpNumberOfCharsWritten = new IntByReference();
            successful = INSTANCE.WriteConsoleW(handle, buffer, buffer.length,
                    lpNumberOfCharsWritten, null);
        }
        if (!successful) {
            System.out.print(message);
        }
    }


    public static String processColorCodes(String string)
    {
        for(String colorcode : Colors.COLORCODES.keySet())
        {
            string = string.replace(colorcode, Colors.COLORCODES.get(colorcode));
        }
        return string;
    }


    public static void clear()
    {
        println("\033c");
    }

    public static void error(String error)
    {
        println("\n⚠️  " + Props.get("error_label") + ": " + Props.get(error));
    }

    public static void error(String msg, String ... args)
    {
        println("\n⚠️  " + Props.get("error_label") + ": " + replaceArgs(Props.get(msg), args));
    }

    public static void success(String msg)
    {
        println("\n✔  " + Props.get("success_label") + ": " + Props.get(msg));
    }

    public static void success(String msg, String ... args)
    {
        println("\n✔  " + Props.get("success_label") + ": " + replaceArgs(Props.get(msg), args));
    }

    public static void printmsg(String msg)
    {
        print(Props.get(msg));
    }

    public static void printmsg(String msg, String ... args)
    {
        print(replaceArgs(Props.get(msg), args));
    }

    public static void printmsgln(String msg)
    {
        println(Props.get(msg));
    }

    public static void printmsgln(String msg, String ... args)
    {
        println(replaceArgs(Props.get(msg), args));
    }


    public static String replaceArgs(String msg, String[] args) {
        for(int i = 0; i < args.length; i++)
        {
            msg = msg.replaceFirst("(?i)%arg" + String.valueOf(i), args[i]);
        }
        return msg;
    }

    public static String prompt(String message)
    {
        printmsg(message);
        Scanner input = new Scanner(System.in);
        String nl = "";
        while(nl.isEmpty()) 
        {
            nl = input.nextLine();
            Console.print(String.format("\033[%dA", 1));
            Console.print(String.format("\033[%dC", Props.get(message).length() -1));
        }
        Console.println("");
        input.close();
        return nl;
    }

    public static String prompt(String message, String defaultInput)
    {
        printmsg(message);
        Scanner input = new Scanner(System.in);
        String nl = "";
        while(nl.isEmpty()) 
        {
            nl = input.nextLine();
            Console.print(String.format("\033[%dA", 1));
            Console.print(String.format("\033[%dC", Props.get(message).length() -1));
            type(defaultInput);
        }
        Console.println("");
        input.close();
        return nl;
    }

    public static String promptMenu(String[] options)
    {       
        return "";
    }

    public static void type(String text)
    {
        try {
            StringSelection stringSelection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);

            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
