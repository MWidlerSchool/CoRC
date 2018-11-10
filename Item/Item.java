package Item;

import GUI.*;
import java.awt.*;

public class Item extends ScreenObj
{
    protected String name;
    
    public String getName(){return name;}
    
    public void setName(String n){name = n;}
    
    public Item(char c)
    {
        this(c, Color.WHITE, Color.BLACK);
    }
    
    public Item(char c, Color fg, Color bg)
    {
        super(c, fg, bg);
    }
}