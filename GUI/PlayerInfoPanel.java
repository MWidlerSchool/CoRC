package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import Actor.*;
import Engine.*;
import MyTools.*;

/*
    Displays information about the player character
*/

public class PlayerInfoPanel extends ScreenPanel
{
    private static int xOrigin = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[0];
    private static int yOrigin = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1];
    
    
    public static void paint(Graphics2D g2d, Font stringFont, Font terminalFont)
    {
        Actor player = GameObj.getPlayer();
        
        // early exit if no player
        if(player == null)
        {
            return;
        }
        
        g2d.setFont(stringFont);
        writeString(g2d, xOrigin, yOrigin, player.getName());
        
        paintHealthBar(g2d, player, terminalFont);
        paintBlockBar(g2d, player, terminalFont);
    }
    
    
    public static void paintHealthBar(Graphics2D g2d, Actor player, Font terminalFont)
    {
        g2d.setFont(terminalFont);
        g2d.setColor(GUIConstants.HEALTH_COLOR);
        writeString(g2d, xOrigin + 1, yOrigin + 2, "Health");
        g2d.drawString(Character.toString((char)16), (xOrigin + 8) * getTileWidth(), (yOrigin + 3) * getTileHeight());
        g2d.drawString(Character.toString((char)17), (xOrigin + 19) * getTileWidth(), (yOrigin + 3) * getTileHeight());
        int[] healthArr = GUITools.getIntensityArray(player.getHealthBlock().getCurHealth(), player.getHealthBlock().getMaxHealth(), 10, 4);
        String[] bar = new String[5];
        bar[0] = Character.toString((char)9608);     // empty
        bar[1] = Character.toString((char)9619);     // 25%
        bar[2] = Character.toString((char)9618);     // 50%
        bar[3] = Character.toString((char)9617);     // 75%
        bar[4] = Character.toString((char)9608);     // 100%
        
        for(int i = 0; i < healthArr.length; i++)
        {
            if(healthArr[i] == 0)
                g2d.setColor(GUIConstants.BLACK);
            else
                g2d.setColor(GUIConstants.HEALTH_COLOR);
            g2d.drawString(bar[healthArr[i]], (xOrigin + 9 + i) * getTileWidth(), (yOrigin + 3) * getTileHeight());
        }
    }
    
    
    public static void paintBlockBar(Graphics2D g2d, Actor player, Font terminalFont)
    {
        g2d.setFont(terminalFont);
        g2d.setColor(GUIConstants.BLOCK_COLOR);
        writeString(g2d, xOrigin + 1, yOrigin + 4, "Block");
        g2d.drawString(Character.toString((char)16), (xOrigin + 8) * getTileWidth(), (yOrigin + 5) * getTileHeight());
        g2d.drawString(Character.toString((char)17), (xOrigin + 19) * getTileWidth(), (yOrigin + 5) * getTileHeight());
        int[] blockArr = GUITools.getIntensityArray(player.getHealthBlock().getCurBlock(), player.getHealthBlock().getMaxBlock(), 10, 4);
        String[] bar = new String[5];
        bar[0] = Character.toString((char)9608);     // empty
        bar[1] = Character.toString((char)9619);     // 25%
        bar[2] = Character.toString((char)9618);     // 50%
        bar[3] = Character.toString((char)9617);     // 75%
        bar[4] = Character.toString((char)9608);     // 100%
        
        for(int i = 0; i < blockArr.length; i++)
        {
            if(blockArr[i] == 0)
                g2d.setColor(GUIConstants.BLACK);
            else
                g2d.setColor(GUIConstants.BLOCK_COLOR);
            g2d.drawString(bar[blockArr[i]], (xOrigin + 9 + i) * getTileWidth(), (yOrigin + 5) * getTileHeight());
        }
    }

}