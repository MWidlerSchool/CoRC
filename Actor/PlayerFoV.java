package Actor;

import MyTools.*;
import Engine.*;
import Map.*;
import GUI.*;

/*
    A class for persistently holding the player's FoV info.
*/

public class PlayerFoV
{
    private static boolean[][] viewArray = null;
    private static boolean updateNeeded = true;
    private static Coord lastPlayerLoc = new Coord();
    
    public static void requestUpdate(){updateNeeded = true;}
    
    // update if one or more have been requested, or if the player has moved
    public static void softUpdate()
    {
        if(GameObj.getPlayer() == null)
            return;
            
        if(GameObj.getPlayer().getLoc().equals(lastPlayerLoc) == false)
        {
            lastPlayerLoc = GameObj.getPlayer().getLoc();
            updateNeeded = true;
        }
        
        if(updateNeeded)
            forceUpdate();
    }
    
    // maybe don't call this one every frame.
    public static void forceUpdate()
    {
        Actor player = GameObj.getPlayer();
        GameMap map = GameObj.getMap();
        if(player == null || map == null)
        {
            viewArray = null;
            return;
        }
            
        int visionRadius = player.getStatBlock().getVisionRadius();
        int arrSize = (player.getStatBlock().getVisionRadius() * 2) + 1;
        int xVisionOffset = player.getLoc().x - visionRadius;
        int yVisionOffset = player.getLoc().y - visionRadius;
        boolean[][] transparencyArr = new boolean[arrSize][arrSize];
        for(int x = 0; x < arrSize; x++)
        for(int y = 0; y < arrSize; y++)
        {
            transparencyArr[x][y] = map.getCell(x + xVisionOffset, y + yVisionOffset).isTransparent();
        }
        viewArray = FieldOfView.getFoV(transparencyArr, new Coord(visionRadius, visionRadius));
    }
    
    public static boolean canSee(Actor a){return canSee(a.getLoc());}
    public static boolean canSee(Coord c){return canSee(c.x, c.y);}
    public static boolean canSee(int x, int y)
    {
        Actor player = GameObj.getPlayer();
        if(viewArray == null || player == null)
            return false;
        int viewRadius = player.getStatBlock().getVisionRadius();
        if(MathTools.getAngbandMetric(x, y, player.getLoc().x, player.getLoc().y) > viewRadius)
            return false;
        
        x -= player.getLoc().x;
        y -= player.getLoc().y;
        x += viewRadius;
        y += viewRadius;
        return viewArray[x][y];
    }
}