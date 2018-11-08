package GUI;

import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FontManager
{
    public static void loadFonts()
    {
        String fileName = "KOMTXT__.ttf";
        try
        {
            InputStream inStream = new BufferedInputStream(new FileInputStream(fileName));
            Font font = Font.createFont(Font.TRUETYPE_FONT, inStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load " + fileName + ". " + ex.toString(), "File Error", JOptionPane.ERROR_MESSAGE); 
        }
        fileName = "Px437_Wyse700b.ttf";
        try
        {
            InputStream inStream = new BufferedInputStream(new FileInputStream(fileName));
            Font font = Font.createFont(Font.TRUETYPE_FONT, inStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load " + fileName + ". " + ex.toString(), "File Error", JOptionPane.ERROR_MESSAGE); 
        }
        fileName = "Px437_Wyse700b-2y.ttf";
        try
        {
            InputStream inStream = new BufferedInputStream(new FileInputStream(fileName));
            Font font = Font.createFont(Font.TRUETYPE_FONT, inStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load " + fileName + ". " + ex.toString(), "File Error", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public static Font getStringFont(int size){return new Font("Komika Text", Font.BOLD, GUITools.pixelsToPoint(size));}
    //public static Font getMapFont(int size){return new Font("Dialog", Font.BOLD, GUITools.pixelsToPoint(size));}
    public static Font getMapFont(int size){return new Font("Px437 Wyse700b", Font.PLAIN, GUITools.pixelsToPoint(size));}
    public static Font getTerminalFont(int size){return new Font("Px437 Wyse700b-2y", Font.PLAIN, GUITools.pixelsToPoint(size));}
}