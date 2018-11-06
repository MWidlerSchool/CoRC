package GUI;

import java.awt.image.*;
import java.awt.*;
import MyTools.*;


public class GUITools
{
    public static BufferedImage copyImage(BufferedImage bImg) 
    {
        ColorModel cm = bImg.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bImg.copyData(bImg.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
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