package GUI;

import MyTools.*;
import Engine.*;
import java.awt.*;
import java.util.*;

// temporarily colors a tiles (overrides the background color)
public class FadeEffect implements GUIConstants
{
	private Coord loc;
	private Vector<Color> colorLine;
	private int lifespan;


	public Coord getLoc(){return new Coord(loc);}
	public Vector<Color> getColorLine(){return colorLine;}
	public int getLifespan(){return lifespan;}


	public void setLoc(Coord l){setLoc(l.x, l.y);}
	public void setLoc(int x, int y){loc = new Coord(x, y);}
	public void setColorLine(Vector<Color> c){colorLine = c;}
	public void setLifespan(int l){lifespan = l;}
    
    public FadeEffect()
    {
        loc = new Coord();
        colorLine = new Vector<Color>();
        lifespan = 0;
    }
    
    // 5 ticks at the original color, then fades over 10 ticks
    public FadeEffect(Coord location, Color initColor)
    {
        this();
        loc = new Coord(location);
        colorLine.add(initColor);
        Color finalColor = GameObj.getMap().getCell(location).getBGColor();
        int r1 = initColor.getRed();
        int g1 = initColor.getGreen();
        int b1 = initColor.getBlue();
        int r2 = finalColor.getRed();
        int g2 = finalColor.getGreen();
        int b2 = finalColor.getBlue();
        int rStep = (r2 - r1) / 9;
        int gStep = (g2 - g1) / 9;
        int bStep = (b2 - b1) / 9;
        for(int i = 0; i < 5; i++)
            colorLine.add(initColor);
        for(int i = 0; i < 10; i++)
        {
            colorLine.add(new Color(r1 + (rStep * i), g1 + (gStep * i), b1 + (bStep * i)));
        }
    }
    
    public Color getColor()
    {
        // safety feature because the display isn't necessariyl in synch with the animation updates
        if(lifespan < colorLine.size())
            return colorLine.elementAt(lifespan);
        else
            return colorLine.lastElement();
    }
    
    public void update()
    {
        lifespan += 1;
    }
    
    public boolean isExpired()
    {
        return lifespan >= colorLine.size();
    }
}