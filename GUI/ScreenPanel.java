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

    
    // writes a string in terminal tiles
    public static void writeString(Graphics2D g2d, int xLocTiles, int yLocTiles, String str)
    {
        char[] arr = str.toCharArray();
        int yLoc = yLocTiles * tileHeight;
        for(int i = 0; i < arr.length; i++)
            g2d.drawImage(terminalTiles[arr[i]], (xLocTiles + i) * tileWidth, yLoc, null);
    }
    
    // fill an area with a specific tile
    public static void fill(Graphics2D g2d, int xStartTiles, int yStartTiles, int widthTiles, int heightTiles, int tileIndex)
    {
        BufferedImage tile = mapTiles[tileIndex];
        for(int x = 0; x < widthTiles; x++)
        for(int y = 0; y < heightTiles; y++)
            g2d.drawImage(tile, (xStartTiles + x) * tileWidth, (yStartTiles + y) * tileHeight, null);
    }
}