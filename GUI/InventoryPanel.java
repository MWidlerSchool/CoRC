package GUI;

import Item.*;
import Actor.*;
import Engine.*;
import java.awt.*;

public class InventoryPanel extends ScreenPanel
{
    public static final int USE_STATE = 0;
    public static final int DROP_STATE = 1;
    
    private static int curState = -1;
    private static int index = 0;
    private static Inventory inv = null;
    
    public static void setState(int newState){curState = newState;}
    public static void setIndex(int i){index = i;}
    
    public static int getState(){return curState;}
    public static int getIndex(){return index;}
    
    public static void paint(Graphics2D g2d, Font stringFont, Font terminalFont)
    {
        g2d.setFont(stringFont);
        g2d.setColor(Color.WHITE);
        writeString(g2d, "Inventory Panel " + curState, 0, 1);
        
        if(GameObj.getPlayer() != null && GameObj.getPlayer().getInventory() != null)
        {
            inv = GameObj.getPlayer().getInventory();
            
            // draw items
            for(int i = 0; i < inv.size(); i++)
            {
                Item item = inv.getItem(i);
                g2d.setFont(terminalFont);
                writeString(g2d, item.getStr(), 2, 3 + (2 * i));
                g2d.setFont(stringFont);
                writeString(g2d, item.getName(), 4, 3 + (2 * i));
            }
            
            // draw index
            if(inv.size() > 0)
            {
                g2d.setFont(terminalFont);
                writeString(g2d, ">", 1, 3 + (2 * index));
            }
        }
    }
    
    public static void incrementIndex()
    {
        if(inv != null)
        {
            index += 1;
            if(index >= inv.size())
                index = 0;
        }
    }
    
    public static void decrementIndex()
    {
        if(inv != null)
        {
            index -= 1;
            if(index < 0)
                index = Math.max(inv.size() - 1, 0);
        }
    }
}