package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import Engine.*;

/*
    Parent class of various screen panels.
*/

public class ScreenPanel
{
	private static BufferedImage[] terminalTiles;
	private static BufferedImage[] mapTiles;
	private static Font stringFont;
	private static int tileWidth;
	private static int tileHeight;
	private static int doubleTileHeight;


	public static BufferedImage[] getTerminalTiles(){return terminalTiles;}
	public static BufferedImage[] getMapTiles(){return mapTiles;}
	public static Font getStringFont(){return stringFont;}
	public static int getTileWidth(){return tileWidth;}
	public static int getTileHeight(){return tileHeight;}
	public static int getDoubleTileHeight(){return doubleTileHeight;}


	public static void setTerminalTiles(BufferedImage[] t){terminalTiles = t;}
	public static void setMapTiles(BufferedImage[] m){mapTiles = m;}
	public static void setStringFont(Font s){stringFont = s;}
    
    
    public static void setTileSize(int ts)
    {
        tileWidth = ts;
        tileHeight = ts;
        doubleTileHeight = ts + ts;
    }

    
    // writes a string in terminal tiles assumes tiles are monospaced
    public static void writeString(Graphics2D g2d, String str, int xLocTiles, int yLocTiles)
    {
        int yLoc = (yLocTiles + 1) * tileHeight;
        g2d.drawString(str, xLocTiles * tileWidth, yLoc);
    }
    
    public static void writeString(Graphics2D g2d, char[] charArr, int xLocTiles, int yLocTiles)
    {
        writeString(g2d, new String(charArr), xLocTiles, yLocTiles);
    }
}