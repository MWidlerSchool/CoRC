package GUI;

import java.awt.*;
import java.awt.image.*;
import MyTools.*;

public class ScreenObj
{
	private String str;
	private Color fgColor;
	private Color bgColor;


	public String getStr(){return str;}
	public Color getFGColor(){return fgColor;}
	public Color getBGColor(){return bgColor;}


	public void setStr(String s){str = s;}
    public void setStr(char s){setStr(Character.toString(s));}
	public void setFGColor(Color f){fgColor = f;}
	public void setBGColor(Color b){bgColor = b;}

    
    
    public ScreenObj(String s)
    {
        str = s;
        fgColor = Color.WHITE;
        bgColor = Color.BLACK;
    }
    
    public ScreenObj(char c, Color fg, Color bg)
    {
        this(c);
        setFGColor(fg);
        setBGColor(bg);
    }
    
    public ScreenObj(char c)
    {
        this(Character.toString(c));
    }
}
