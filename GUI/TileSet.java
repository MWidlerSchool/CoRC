package GUI;

import java.awt.image.*;
import java.awt.*;

/*
    A static class for holding map tiles, terminal tiles, and the string font.
*/

public class TileSet
{
    private static BufferedImage[] mapTiles = null;
    private static BufferedImage[] terminalTiles = null;
    private static int tileSize = GUIConstants.DEFAULT_TILE_SIZE;
    private static Font stringFont = null;


	public static BufferedImage[] getMapTiles(){return mapTiles;}
	public static BufferedImage[] getTerminalTiles(){return terminalTiles;}
    public static int getTileSize(){return tileSize;}
    public static Font getStringFont(){return stringFont;}
    
    public static void set(int size)
    {
        tileSize = size;
        mapTiles = ImageLoader.loadMapFont(tileSize);
        terminalTiles = ImageLoader.loadTerminalFont(tileSize);
        stringFont = ImageLoader.getStringFont(tileSize);
    }
    
    public static BufferedImage getMapTile(int i){return GUITools.copyImage(mapTiles[i]);}
    public static BufferedImage getMapTile(char c){return GUITools.copyImage(mapTiles[(int)c]);}
    public static BufferedImage getTerminalTile(int i){return GUITools.copyImage(terminalTiles[i]);}
    public static BufferedImage getTerminalTile(char c){return GUITools.copyImage(terminalTiles[(int)c]);}
}