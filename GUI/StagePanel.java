package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import Map.*;
import Item.*;
import Actor.*;
import Engine.*;
import MyTools.*;

/*
    Displays information about the player character
*/

public class StagePanel extends ScreenPanel
{
    private static int xOrigin = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[0];
    private static int yOrigin = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1];
    private static String[][] stringArr;
    private static Color[][] fgColorArr;
    private static Color[][] bgColorArr;
    
    public static void init()
    {
        stringArr = new String[GUIConstants.MAP_DISPLAY_WIDTH][GUIConstants.MAP_DISPLAY_HEIGHT];
        fgColorArr = new Color[GUIConstants.MAP_DISPLAY_WIDTH][GUIConstants.MAP_DISPLAY_HEIGHT];
        bgColorArr = new Color[GUIConstants.MAP_DISPLAY_WIDTH][GUIConstants.MAP_DISPLAY_HEIGHT];
    }
    
    
    public static void paint(Graphics2D g2d, Font mapFont)
    {        
        g2d.setFont(mapFont);
        Actor player = GameObj.getPlayer();
        GameMap map = GameObj.getMap();
        if(player == null || map == null)
            return;
            
        int tileSize = CoRCFrame.getTileSize();
        int xCorner = player.getLoc().x - (GUIConstants.MAP_DISPLAY_WIDTH / 2);
        int yCorner = player.getLoc().y - (GUIConstants.MAP_DISPLAY_HEIGHT / 2);
        
        // load ground tiles
        for(int x = 0; x < GUIConstants.MAP_DISPLAY_WIDTH; x++)
        for(int y = 0; y < GUIConstants.MAP_DISPLAY_HEIGHT; y++)
        {
            stringArr[x][y] = map.getCell(x + xCorner, y + yCorner).getStr();
            if(PlayerFoV.canSee(x + xCorner, y + yCorner))
            {
                bgColorArr[x][y] = map.getCell(x + xCorner, y + yCorner).getBGColor();
                fgColorArr[x][y] = map.getCell(x + xCorner, y + yCorner).getFGColor();
            }
            else
            {
                bgColorArr[x][y] = GUIConstants.BLACK;
                fgColorArr[x][y] = GUIConstants.OOB_COLOR;
            }
        }
        
        // load items
        for(int x = 0; x < GUIConstants.MAP_DISPLAY_WIDTH; x++)
        for(int y = 0; y < GUIConstants.MAP_DISPLAY_HEIGHT; y++)
        {
            if(PlayerFoV.canSee(x + xCorner, y + yCorner) && map.getCell(x + xCorner, y + yCorner).hasItem())
            {
                Item item = map.getCell(x + xCorner, y + yCorner).getItem();
                stringArr[x][y] = item.getStr();
                fgColorArr[x][y] = item.getFGColor();
            }
        }
        
        // load actors
        Vector<Actor> actorList = GameObj.getActorList();
        for(int i = 0; i < actorList.size(); i++)
        {
            Actor actor = actorList.elementAt(i);
            if(PlayerFoV.canSee(actor.getLoc()))
            {
                stringArr[actor.getLoc().x - xCorner][actor.getLoc().y - yCorner] = actor.getStr();
                fgColorArr[actor.getLoc().x - xCorner][actor.getLoc().y - yCorner] = actor.getFGColor();
            }
        }
        
        // paint area
        int xPaintOrigin = 0;
        int yPaintOrigin = 0;
        for(int x = 0; x < GUIConstants.MAP_DISPLAY_WIDTH; x++)
        for(int y = 0; y < GUIConstants.MAP_DISPLAY_HEIGHT; y++)
        {
            // background first
            xPaintOrigin = (x + GUIConstants.MAP_DISPLAY_ORIGIN[0]) * tileSize;
            yPaintOrigin = (y + GUIConstants.MAP_DISPLAY_ORIGIN[1]) * tileSize; // no +1 because it's not a string
            g2d.setColor(bgColorArr[x][y]);
            g2d.fillRect(xPaintOrigin, yPaintOrigin, tileSize, tileSize);
        }
        for(int x = 0; x < GUIConstants.MAP_DISPLAY_WIDTH; x++)
        for(int y = 0; y < GUIConstants.MAP_DISPLAY_HEIGHT; y++)
        {
            // then the foreground
            String curString = stringArr[x][y];
            xPaintOrigin = (x + GUIConstants.MAP_DISPLAY_ORIGIN[0]) * tileSize;
            yPaintOrigin = (y + GUIConstants.MAP_DISPLAY_ORIGIN[1] + 1) * tileSize;
            if(fgColorArr[x][y] == null)
                g2d.setColor(GUIConstants.OOB_COLOR);
            else
                g2d.setColor(fgColorArr[x][y]);
            // offset for wide tiles
            if(g2d.getFontMetrics().stringWidth(stringArr[x][y]) > tileSize)
            {
                xPaintOrigin -= (g2d.getFontMetrics().stringWidth(stringArr[x][y]) - tileSize);
            }
            g2d.drawString(stringArr[x][y], xPaintOrigin, yPaintOrigin);
        }
    }

}