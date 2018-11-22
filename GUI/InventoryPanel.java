package GUI;

import Item.*;
import Actor.*;
import Engine.*;
import java.awt.*;

public class InventoryPanel extends ScreenPanel
{
    public static void paint(Graphics2D g2d, Font stringFont, Font terminalFont)
    {
        g2d.setFont(stringFont);
        g2d.setColor(Color.WHITE);
        writeString(g2d, "Inventory Panel", 0, 1);
        
        if(GameObj.getPlayer() != null && GameObj.getPlayer().getInventory() != null)
        {
            Inventory inv = GameObj.getPlayer().getInventory();
            for(int i = 0; i < inv.size(); i++)
            {
                Item item = inv.getItem(i);
                g2d.setFont(terminalFont);
                writeString(g2d, item.getStr(), 2, 3 + (2 * i));
                g2d.setFont(stringFont);
                writeString(g2d, item.getName(), 4, 3 + (2 * i));
            }
        }
    }
}