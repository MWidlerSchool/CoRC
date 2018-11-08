package Map;

import Engine.*;
import GUI.*;
import Actor.*;

public class Door extends MapCell implements Usable
{
	private boolean open;
	private boolean locked;


	public boolean isOpen(){return open;}
	public boolean isLocked(){return locked;}


	public void setOpen(boolean o){open = o;}
	public void setLocked(boolean l){locked = l;}

    
    public Door()
    {
        super('#');
        open = false;
        locked = false;
        setStr(GUIConstants.CLOSED_DOOR_STRING);
    }
    
    
    public void use(Actor user)
    {
        if(!locked)
        {
            open = !open;
            setHighPassable(open);
            setLowPassable(open);
            setTransparent(open);
            
            if(open)
                setStr(GUIConstants.OPEN_DOOR_STRING);
            else
                setStr(GUIConstants.CLOSED_DOOR_STRING);
        }
    }
}