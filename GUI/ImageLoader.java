package GUI;

import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ImageLoader
{
    public static void loadFont()
    {
        String fileName = "GUI/Fonts/KOMTXT__.ttf";
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
    
    public static Font getStringFont(int size)
    {
        return new Font("Komika Text", Font.PLAIN, (size * 3) / 2);
    }
    
    public static BufferedImage[] loadMapFont(int size)
    {
        String fileName = String.format("GUI/Fonts/map_font_%dx%d.bmp", size, size);
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(fileName));
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load " + fileName + ".", "File Error", JOptionPane.ERROR_MESSAGE); 
        }
        BufferedImage imageList[] = new BufferedImage[(16 * 16)];
        for(int x = 0; x < 16; x++)
        for(int y = 0; y < 16; y++)
        {
            imageList[x + (y * 16)] = img.getSubimage(x * size, y * size, size, size);
        }
        return imageList;
    }
    
    public static BufferedImage[] loadTerminalFont(int size)
    {
        int width = size;
        int height = size * 2;
        String fileName = String.format("GUI/Fonts/terminal_font_comic_%dx%d.bmp", width, height);
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(fileName));
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Could not load " + fileName + ".", "File Error", JOptionPane.ERROR_MESSAGE); 
        }
        BufferedImage imageList[] = new BufferedImage[(16 * 16)];
        for(int x = 0; x < 16; x++)
        for(int y = 0; y < 16; y++)
            imageList[x + (y * 16)] = img.getSubimage(x * width, y * height, width, height);
        return imageList;
    }
    
}
