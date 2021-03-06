package GUI;

import java.awt.image.*;
import java.awt.*;
import MyTools.*;
import Engine.*;


public class GUITools
{
    
    public static boolean isOnStage(Coord c){return isOnStage(c.x, c.y);}
    public static boolean isOnStage(int x, int y)
    {
        Coord playerLoc = GameObj.getPlayer().getLoc();
        if(x >= playerLoc.x - (GUIConstants.MAP_DISPLAY_WIDTH / 2) &&
           x <= playerLoc.x + (GUIConstants.MAP_DISPLAY_WIDTH / 2) + 1&&
           y >= playerLoc.y - (GUIConstants.MAP_DISPLAY_HEIGHT / 2) &&
           y <= playerLoc.y + (GUIConstants.MAP_DISPLAY_HEIGHT / 2) + 1)
            return true;
        return false;
    }

    
    // returns an array on n ints, ranging from 0 to i, where i = increment
    public static int[] getIntensityArray(int cur, int max, int cells, int increment)
    {
        int[] iArr = new int[cells];
        double cellMax = (double)max / (double)cells;
        double tempVal = (double)cur;
        for(int i = 0; i < cells; i++)
        {
            if(tempVal >= cellMax)
            {
                iArr[i] = increment;
                tempVal -= cellMax;
            }
            else if(tempVal == 0.0)
            {
                iArr[i] = 0;
                // no need to change tempVal
            }
            else
            {
                // at least 1 if above 0.0, at most (increment - 1) if below increment
                double dVal = tempVal / cellMax;
                iArr[i] = Math.max(MathTools.roundToInt(dVal * increment), 1);
                iArr[i] = iArr[i] = Math.min(iArr[i], increment - 1);
                tempVal = 0.0;
            }
        }
        return iArr;
    }
    
    
    public static Color[] getColorArray(Color base)
    {
        Color[] cArr = new Color[10];
        int baseRed = base.getRed();
        int baseBlue = base.getBlue();
        int baseGreen = base.getGreen();
        
        for(int i = 0; i < 10; i++)
        {
            cArr[9 - i] = new Color(baseRed / (i + 1), baseGreen / (i + 1), baseBlue / (i + 1));
        }
        return cArr;
    }
    
    public static int pixelsToPoint(int px)
    {
        return (px * 4) / 3;
    }
    
    public static int pointToPixels(int pt)
    {
        return (pt * 3) / 4;
    }

    
    
    // test function
    public static void main(String[] args)
    {
        int[] intArr = getIntensityArray(15, 20, 10, 4);
        for(int i = 0; i < intArr.length; i++)
        {
            System.out.print(intArr[i] + " ");
        }
    }
}