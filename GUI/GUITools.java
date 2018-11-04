package GUI;

import java.awt.image.*;
import java.awt.*;


public class GUITools
{
    public static BufferedImage copyImage(BufferedImage bImg) 
    {
        ColorModel cm = bImg.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bImg.copyData(bImg.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
}