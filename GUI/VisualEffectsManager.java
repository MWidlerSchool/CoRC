package GUI;

import Engine.*;
import java.awt.event.*;
import java.util.*;

public class VisualEffectsManager implements GUIConstants
{
    private static int xShake = 0;
    private static int yShake = 0;
    private static int shakeDuration = 0;
    private static Vector<VisualEffect> veList = new Vector<VisualEffect>();
    private static Vector<FadeEffect> feList = new Vector<FadeEffect>();
    
    public static int getXShake(){return xShake;}
    public static int getYShake(){return yShake;}
    public static Vector<VisualEffect> getVEList(){return veList;}
    public static Vector<FadeEffect> getFEList(){return feList;}
    
    public static void setScreenShake(int dur)
    {
        shakeDuration = dur;
    }
    
    public static void add(VisualEffect ve)
    {
        veList.add(ve);
    }
    
    public static void add(Vector<VisualEffect> newList)
    {
        for(int i = 0; i < newList.size(); i++)
            veList.add(newList.elementAt(i));
    }
    
    public static void add(FadeEffect fe)
    {
        feList.add(fe);
    }
    
    // can't do an add(Vector<FadeEffect>)
    
    private static void shake()
    {
        xShake = RNG.nextInt((MAX_SCREEN_SHAKE * 2) + 1) - MAX_SCREEN_SHAKE;
        yShake = RNG.nextInt((MAX_SCREEN_SHAKE * 2) + 1) - MAX_SCREEN_SHAKE;
    }
    
    public void update() // kicked by CoRCFrame
    {
        if(shakeDuration > 0)
        {
            shakeDuration -= 1;
            shake();
        }
        
        // visual effects
        for(int i = 0; i < veList.size(); i++)
        {
            if(veList.elementAt(i).isExpired())
            {
                veList.removeElementAt(i);
                i -= 1;
            }
            else
            {
                veList.elementAt(i).update();
            }
        }
        
        // fade effects
        for(int i = 0; i < feList.size(); i++)
        {
            if(feList.elementAt(i).isExpired())
            {
                feList.removeElementAt(i);
                i -= 1;
            }
            else
            {
                feList.elementAt(i).update();
            }
        }
    }
}