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
    
    
    public static void paint(Graphics2D g2d)
    {
        Actor player = GameObj.getPlayer();
        
        // early exit if no player
        if(player == null)
        {
            fill(g2d, xOrigin, yOrigin, GUIConstants.PLAYER_INFO_DISPLAY_WIDTH, GUIConstants.PLAYER_INFO_DISPLAY_HEIGHT, 0);
            return;
            
        }

        writeString(g2d, xOrigin, yOrigin, player.getName());
        writeString(g2d, xOrigin, yOrigin + 4, "Block:  ");
        
        paintHealthBar(g2d, player);
        paintBlockBar(g2d, player);
    }
    
    
    public static void paintHealthBar(Graphics2D g2d, Actor player)
    {
        writeString(g2d, xOrigin, yOrigin + 2, "Health: [          ]");
        int[] healthArr = GUITools.getIntensityArray(player.getHealthBlock().getCurHealth(), player.getHealthBlock().getMaxHealth(), 10, 4);
        BufferedImage[] bar = new BufferedImage[5];
        bar[0] = GUITools.copyImage(getTerminalTiles()[0x00]);     // empty
        bar[1] = GUITools.copyImage(getTerminalTiles()[0xB0]);     // 25%
        bar[2] = GUITools.copyImage(getTerminalTiles()[0xB1]);     // 50%
        bar[3] = GUITools.copyImage(getTerminalTiles()[0xB2]);     // 75%
        bar[4] = GUITools.copyImage(getTerminalTiles()[0xDB]);     // 100%
        
        for(int i = 0; i < 5; i++)
            ColorChanger.change(bar[i], GUIConstants.WHITE, GUIConstants.HEALTH_COLOR);
        
        for(int i = 0; i < healthArr.length; i++)
        {
            g2d.drawImage(bar[healthArr[i]], (xOrigin + 9 + i) * getTileWidth(), (yOrigin + 2) * getTileHeight(), null);
        }
    }
    
    
    public static void paintBlockBar(Graphics2D g2d, Actor player)
    {
        writeString(g2d, xOrigin, yOrigin + 4, "Block:  [          ]");
        int[] healthArr = GUITools.getIntensityArray(player.getHealthBlock().getCurBlock(), player.getHealthBlock().getMaxBlock(), 10, 4);
        BufferedImage[] bar = new BufferedImage[5];
        bar[0] = GUITools.copyImage(getTerminalTiles()[0x00]);     // empty
        bar[1] = GUITools.copyImage(getTerminalTiles()[0xB0]);     // 25%
        bar[2] = GUITools.copyImage(getTerminalTiles()[0xB1]);     // 50%
        bar[3] = GUITools.copyImage(getTerminalTiles()[0xB2]);     // 75%
        bar[4] = GUITools.copyImage(getTerminalTiles()[0xDB]);     // 100%
        
        for(int i = 0; i < 5; i++)
            ColorChanger.change(bar[i], GUIConstants.WHITE, GUIConstants.BLOCK_COLOR);
        
        for(int i = 0; i < healthArr.length; i++)
        {
            g2d.drawImage(bar[healthArr[i]], (xOrigin + 9 + i) * getTileWidth(), (yOrigin + 4) * getTileHeight(), null);
        }
    }

}