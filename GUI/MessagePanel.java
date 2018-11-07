package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/*
    Handles incoming messages and displays them to the message area.
*/

public class MessagePanel extends ScreenPanel
{
    private static Vector<String> stringList = new Vector<String>();
    private static int maxStrings = 5;
    
    public static void paint(Graphics2D g2d, Font stringFont)
    {
        g2d.setFont(stringFont);
        g2d.setColor(GUIConstants.WHITE);
        int tileSize = CoRCFrame.getTileSize();
        int reps = Math.min(maxStrings, stringList.size());
        int tileWidth = tileSize;
        int tileHeight = tileSize + tileSize;
        int yOrigin = tileSize * 2;
        
        for(int i = 0; i < reps; i++)
        {
            g2d.drawString(stringList.elementAt(reps - i - 1), tileWidth, yOrigin + (tileHeight * i));
        }
        
    }
    
    public static void add(String newStr)
    {
        stringList.add(0, newStr);
        if(stringList.size() > maxStrings)
            stringList.removeElementAt(maxStrings);
    }

}