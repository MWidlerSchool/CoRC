package GUI;

import java.awt.*;
import java.awt.image.*;
import MyTools.*;

public class ScreenObj
{
	private BufferedImage image;
	private Color fgColor;
	private Color bgColor;
    private int width;
    private int height;
    private Coord loc;


	public BufferedImage getImage(){return image;}
	public Color getFGColor(){return fgColor;}
	public Color getBGColor(){return bgColor;}
    public int getHeight(){return height;}
    public int getWidth(){return width;}
    public Coord getLoc(){return new Coord(loc);}


	public void setImage(BufferedImage i){image = i;}
    public void setLoc(Coord c){setLoc(c.x, c.y);}
    public void setLoc(int x, int y){loc.x = x; loc.y = y;}

    
    
    public ScreenObj(BufferedImage i)
    {
        image = i;
        fgColor = Color.WHITE;
        bgColor = Color.BLACK;
        width = i.getWidth();
        height = i.getHeight();
        loc = new Coord();
    }
    
    public ScreenObj(BufferedImage i, Color fg, Color bg)
    {
        this(i);
        setFGColor(fg);
        setBGColor(bg);
    }
    
	public void setFGColor(Color f)
    {
        ColorChanger.change(image, fgColor, f);
        fgColor = f;
    }
    
	public void setBGColor(Color b)
    {
        ColorChanger.change(image, bgColor, b);
        bgColor = b;
    }
}