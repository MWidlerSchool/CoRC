package GUI;

import MyTools.*;
import Engine.*;
import java.util.*;
import java.awt.*;


public class VisualEffectsFactory implements GUIConstants
{
    public static Vector<VisualEffect> getImpact(Coord c)
    {
        Vector<VisualEffect> list = new Vector<VisualEffect>();
        for(int i = 0; i < 10; i++)
        {
            VisualEffect ve = new VisualEffect("*");
            ve.setFGColor(STANDARD_IMPACT_COLOR);
            double maxSpeed = .2;
            double xs = (maxSpeed * RNG.nextDouble());
            double ys = (maxSpeed * RNG.nextDouble());
            if(RNG.nextBoolean())
                xs *= -1.0;
            if(RNG.nextBoolean())
                ys *= -1.0;
            ve.setSpeed(xs, ys);
            ve.setScreenLoc(c);
            ve.setMaxLifespan(TICKS_PER_SECOND / 2);
            list.add(ve);
        }
        return list;
    }
    
    public static VisualEffect getFloatMessage(String str, Color color, Coord loc)
    {
        VisualEffect ve = new VisualEffect(str);
        ve.setFGColor(color);
        ve.setSpeed(0, FLOAT_SPEED);
        ve.setScreenLoc(loc);
        ve.setMaxLifespan(TICKS_PER_SECOND);
        return ve;
    }
}