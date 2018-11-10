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
    
    private static String bar[] = {Character.toString((char)9608),
                                   Character.toString((char)9617),
                                   Character.toString((char)9618),
                                   Character.toString((char)9619),
                                   Character.toString((char)9608)};
    
    
    public static void paint(Graphics2D g2d, Font stringFont, Font terminalFont)
    {
        Actor player = GameObj.getPlayer();
        
        // early exit if no player
        if(player == null)
        {
            return;
        }
        
        g2d.setFont(stringFont);
        writeString(g2d, xOrigin + 1, yOrigin + 1, player.getName());
        
        paintHealthBar(g2d, player, terminalFont, stringFont);
        paintBlockBar(g2d, player, terminalFont, stringFont);
        paintStaminaBar(g2d, player, terminalFont, stringFont);
    }
    
    
    public static void paintHealthBar(Graphics2D g2d, Actor player, Font terminalFont, Font stringFont)
    {
        g2d.setFont(stringFont);
        g2d.setColor(GUIConstants.HEALTH_COLOR);
        writeString(g2d, xOrigin + 1, yOrigin + 4, "Health");
        g2d.setFont(terminalFont);
        g2d.drawString(Character.toString((char)16), (xOrigin + 10) * getTileWidth(), (yOrigin + 5) * getTileHeight());
        g2d.drawString(Character.toString((char)17), (xOrigin + 21) * getTileWidth(), (yOrigin + 5) * getTileHeight());
        int[] healthArr = GUITools.getIntensityArray(player.getResourceBlock().getCurHealth(), player.getResourceBlock().getMaxHealth(), 10, 4);
        
        for(int i = 0; i < healthArr.length; i++)
        {
            if(healthArr[i] == 0)
                g2d.setColor(GUIConstants.BLACK);
            else
                g2d.setColor(GUIConstants.HEALTH_COLOR);
            g2d.drawString(bar[healthArr[i]], (xOrigin + 11 + i) * getTileWidth(), (yOrigin + 5) * getTileHeight());
        }
    }
    
    public static void paintBlockBar(Graphics2D g2d, Actor player, Font terminalFont, Font stringFont)
    {
        g2d.setFont(stringFont);
        g2d.setColor(GUIConstants.BLOCK_COLOR);
        writeString(g2d, xOrigin + 1, yOrigin + 6, "Block");
        g2d.setFont(terminalFont);
        g2d.drawString(Character.toString((char)16), (xOrigin + 10) * getTileWidth(), (yOrigin + 7) * getTileHeight());
        g2d.drawString(Character.toString((char)17), (xOrigin + 21) * getTileWidth(), (yOrigin + 7) * getTileHeight());
        int[] blockArr = GUITools.getIntensityArray(player.getResourceBlock().getCurBlock(), player.getResourceBlock().getMaxBlock(), 10, 4);
        
        for(int i = 0; i < blockArr.length; i++)
        {
            if(blockArr[i] == 0)
                g2d.setColor(GUIConstants.BLACK);
            else
                g2d.setColor(GUIConstants.BLOCK_COLOR);
            g2d.drawString(bar[blockArr[i]], (xOrigin + 11 + i) * getTileWidth(), (yOrigin + 7) * getTileHeight());
        }
    }
    
    public static void paintStaminaBar(Graphics2D g2d, Actor player, Font terminalFont, Font stringFont)
    {
        g2d.setFont(stringFont);
        g2d.setColor(GUIConstants.STAMINA_COLOR);
        writeString(g2d, xOrigin + 1, yOrigin + 8, "Stamina");
        g2d.setFont(terminalFont);
        g2d.drawString(Character.toString((char)16), (xOrigin + 10) * getTileWidth(), (yOrigin + 9) * getTileHeight());
        g2d.drawString(Character.toString((char)17), (xOrigin + 21) * getTileWidth(), (yOrigin + 9) * getTileHeight());
        int[] staminaArr = GUITools.getIntensityArray(player.getResourceBlock().getCurStamina(), player.getResourceBlock().getMaxStamina(), 10, 4);
        
        for(int i = 0; i < staminaArr.length; i++)
        {
            if(staminaArr[i] == 0)
                g2d.setColor(GUIConstants.BLACK);
            else
                g2d.setColor(GUIConstants.STAMINA_COLOR);
            g2d.drawString(bar[staminaArr[i]], (xOrigin + 11 + i) * getTileWidth(), (yOrigin + 9) * getTileHeight());
        }
    }

}