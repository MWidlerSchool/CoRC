package GUI;

import MyTools.*;
import Engine.*;
import java.awt.*;

public class VisualEffect extends ScreenObj implements GUIConstants
{
	private Coord screenLoc;    // in tiles
	private double xOffset;     // in tiles
	private double yOffset;     // in tiles
	private int maxLifespan;    // in ticks
	private int curLifespan;    // in ticks
	private double xSpeed;      // in tiles per tick
	private double ySpeed;      // in tiles per tick
    private boolean drawBGColor;    // background is a box behind the string


	public Coord getScreenLoc(){return new Coord(screenLoc);}
	public double getXOffset(){return xOffset;}
	public double getYOffset(){return yOffset;}
	public int getMaxLifespan(){return maxLifespan;}
	public int getCurLifespan(){return curLifespan;}
	public double getXSpeed(){return xSpeed;}
	public double getYSpeed(){return ySpeed;}
    public boolean drawBGColor(){return drawBGColor;}


	public void setScreenLoc(Coord s){setScreenLoc(s.x, s.y);}
	public void setScreenLoc(int x, int y){screenLoc = new Coord(x, y);}
	public void setXOffset(double x){xOffset = x;}
	public void setYOffset(double y){yOffset = y;}
	public void setMaxLifespan(int m){maxLifespan = m;}
	public void setCurLifespan(int c){curLifespan = c;}
	public void setXSpeed(double x){xSpeed = x;}
	public void setYSpeed(double y){ySpeed = y;}
    public void setSpeed(double x, double y){setXSpeed(x); setYSpeed(y);}
    public void setDrawBGColor(boolean d){drawBGColor = d;}


    
    public VisualEffect(String s, Color f)
    {
        super(s, f, Color.BLACK);
        screenLoc = new Coord();
        xOffset = 0.0;
        yOffset = 0.0;
        maxLifespan = TICKS_PER_SECOND;
        curLifespan = 0;
        xSpeed = 0.0;
        ySpeed = 0.0;
        drawBGColor = false;
    }
    public VisualEffect(String s){this(s, Color.WHITE);}
    
    public boolean isExpired()
    {
        return curLifespan >= maxLifespan;
    }
    
    public void update()
    {
        xOffset += xSpeed;
        yOffset += ySpeed;
        curLifespan += 1;
        rectify();
    }
    
    public void rectify()
    {
        if(xOffset < -.5)
        {
            xOffset += 1.0;
            screenLoc.x -= 1;
        }
        else if(xOffset > .5)
        {
            xOffset -= 1.0;
            screenLoc.x += 1;
        }
        if(yOffset < -.5)
        {
            yOffset += 1.0;
            screenLoc.y -= 1;
        }
        else if(yOffset > .5)
        {
            yOffset -= 1.0;
            screenLoc.y += 1;
        }
    }
    
    public static VisualEffect getTestEffect(Coord origin)
    {
        VisualEffect ve = new VisualEffect("*");
        double maxSpeed = .2;
        double xs = (maxSpeed * RNG.nextDouble());
        double ys = (maxSpeed * RNG.nextDouble());
        if(RNG.nextBoolean())
            xs *= -1.0;
        if(RNG.nextBoolean())
            ys *= -1.0;
        ve.setSpeed(xs, ys);
        ve.setScreenLoc(origin);
        ve.setMaxLifespan(TICKS_PER_SECOND / 2);
        return ve;
    }
}