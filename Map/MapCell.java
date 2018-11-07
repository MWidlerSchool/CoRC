package Map;

import GUI.*;
import java.awt.*;
import MyTools.*;
import java.awt.image.*;

public class MapCell extends ScreenObj
{
	private boolean highPassable;
	private boolean lowPassable;
	private boolean transparent;
    
    public static MapCell NULL = new MapCell();


	public boolean isHighPassable(){return highPassable;}
	public boolean isLowPassable(){return lowPassable;}
	public boolean isTransparent(){return transparent;}


	public void setHighPassable(boolean h){highPassable = h;}
	public void setLowPassable(boolean l){lowPassable = l;}
	public void setTransparent(boolean t){transparent = t;}


    public MapCell()
    {
        this(' ');
    }
    
    public MapCell(char c)
    {
        super(c);
        
        // transparency
        switch(c)
        {
            case '_' :
            case '.' :
            case ':' :
            case '=' : setTransparent(true);
        }
        
        // high passability
        switch(c)
        {
            case '_' :
            case '.' :
            case '=' : setHighPassable(true);
        }
        
        // low passability
        switch(c)
        {
            case '.' : setLowPassable(true);
        }
        
        // change period to bullet
        if(c == '.')
            setStr(GUIConstants.BULLET_CHAR);
    }
}