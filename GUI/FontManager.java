package GUI;

import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FontManager implements GUIConstants
{
    public static void loadFonts()
    {
        // string font
        try
        {
            InputStream inStream = new BufferedInputStream(new FileInputStream(STRING_FONT + ".ttf"));
            Font font = Font.createFont(Font.TRUETYPE_FONT, inStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load " + STRING_FONT + ". " + ex.toString(), "File Error", JOptionPane.ERROR_MESSAGE); 
        }
        
        // map font
        try
        {
            InputStream inStream = new BufferedInputStream(new FileInputStream(MAP_FONT + ".ttf"));
            Font font = Font.createFont(Font.TRUETYPE_FONT, inStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load " + MAP_FONT + ". " + ex.toString(), "File Error", JOptionPane.ERROR_MESSAGE); 
        }
        
        // terminal font
        try
        {
            InputStream inStream = new BufferedInputStream(new FileInputStream(TERMINAL_FONT + ".ttf"));
            Font font = Font.createFont(Font.TRUETYPE_FONT, inStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load " + TERMINAL_FONT + ". " + ex.toString(), "File Error", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public static Font getStringFont(int size){return new Font(STRING_FONT, Font.BOLD, (int)(size * 2));}//GUITools.pixelsToPoint(size));}
    //public static Font getMapFont(int size){return new Font("Dialog", Font.BOLD, GUITools.pixelsToPoint(size));}
    public static Font getMapFont(int size){return new Font(MAP_FONT, Font.PLAIN, GUITools.pixelsToPoint((int)(size * .9)));}
    public static Font getTerminalFont(int size){return new Font(TERMINAL_FONT, Font.PLAIN, (int)(size * 2));}//GUITools.pixelsToPoint(size));}
}