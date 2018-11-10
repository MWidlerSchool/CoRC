package Map;

import GUI.*;
import Item.*;
import java.awt.*;
import MyTools.*;
import java.awt.image.*;

public class MapCell extends ScreenObj
{
	private boolean highPassable;
	private boolean lowPassable;
	private boolean transparent;
    private Item item;
    
    public static MapCell NULL = new MapCell();


	public boolean isHighPassable(){return highPassable;}
	public boolean isLowPassable(){return lowPassable;}
	public boolean isTransparent(){return transparent;}
    public Item getItem(){return item;}


	public void setHighPassable(boolean h){highPassable = h;}
	public void setLowPassable(boolean l){lowPassable = l;}
	public void setTransparent(boolean t){transparent = t;}
    public void setItem(Item i){item = i;}


    public MapCell()
    {
        this(' ');
    }
    
    public MapCell(char c)
    {
        super(c);
        item = null;
        
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
        setBGColor(GUIConstants.VERY_DARK_GRAY.darker());
    }
    
    public boolean hasItem()
    {
        if(item == null)
            return false;
        return true;
    }
    
    public Item takeItem()
    {
        Item val = item;
        item = null;
        return val;
    }
}