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
    
    public static void paint(Graphics2D g2d, BufferedImage[] terminalTiles, int tileSize)
    {
        int reps = Math.min(maxStrings, stringList.size());
        int tileWidth = tileSize;
        int tileHeight = tileSize + tileSize;
        int yOrigin = tileSize;
        
        for(int i = 0; i < reps; i++)
        {
            char[] arr = stringList.elementAt(reps - i - 1).toCharArray();
            for(int j = 0; j < arr.length; j++)
                g2d.drawImage(terminalTiles[arr[j]], (j + 1) * tileWidth, yOrigin + (i * tileHeight), null);
            for(int j = arr.length; j < GUIConstants.MAX_MESSAGE_SIZE; j++)
                g2d.drawImage(terminalTiles[0], (j + 1) * tileWidth, yOrigin + (i * tileHeight), null);
        }
        
    }
    
    public static void add(String newStr)
    {
        stringList.add(0, newStr);
        if(stringList.size() > maxStrings)
            stringList.removeElementAt(maxStrings);
    }

}