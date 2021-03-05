package utils;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

public class Tray {

    public Tray(String title, String message, String icon_path) {
        try {
            displayTray(icon_path,"", "", title, message, MessageType.NONE);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public Tray(String title, String message, MessageType type) {
        try {
            displayTray("","", "", title, message, type);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    Tray(String image_path, String image_tooltip, String tooltip, String title, String message, MessageType type) {
        try {
            displayTray(image_path,image_tooltip, tooltip, title, message, type);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void displayTray(String image_path, String image_tooltip, String tooltip, String title, String message, MessageType type) throws AWTException {

        SystemTray tray = SystemTray.getSystemTray();
        
        try
        {
            Image image = ImageIO.read(getClass().getResource(image_path));
            
            TrayIcon trayIcon = new TrayIcon(image, image_tooltip);
            trayIcon.setImageAutoSize(true);
            
            trayIcon.setToolTip(tooltip);
            tray.add(trayIcon);
    
            trayIcon.displayMessage(title, message, type);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        };
    }
}
