package GUI;

import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpriteLoader
{
    
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
            JOptionPane.showMessageDialog(null, "Could not find " + fileName + ".", "File Error", JOptionPane.ERROR_MESSAGE); 
        }
        BufferedImage imageList[] = new BufferedImage[(16 * 16)];
        for(int x = 0; x < 16; x++)
        for(int y = 0; y < 16; y++)
            imageList[x + (y * 16)] = img.getSubimage(x * size, y * size, size, size);
        return imageList;
    }
    
}
