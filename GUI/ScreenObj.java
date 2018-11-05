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


	public BufferedImage getImage(){return image;}
	public Color getFGColor(){return fgColor;}
	public Color getBGColor(){return bgColor;}
    public int getHeight(){return height;}
    public int getWidth(){return width;}


	public void setImage(BufferedImage i){image = i;}

    
    
    public ScreenObj(BufferedImage i)
    {
        image = i;
        fgColor = Color.WHITE;
        bgColor = Color.BLACK;
        width = i.getWidth();
        height = i.getHeight();
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