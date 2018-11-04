package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import MyTools.*;
import Actor.*;
import Engine.*;
import Map.*;
import java.util.*;

public class MainPanel extends JPanel
{
	private JFrame parentFrame;
    private BufferedImage[][] mapAreaArray;

	public JFrame getParentFrame(){return parentFrame;}
    

    public MainPanel(JFrame parent)
    {
        super();
        parentFrame = parent;
        parentFrame.add(this);
        setFocusable(false);
        setBackground(Color.BLACK);
        mapAreaArray = new BufferedImage[GUIConstants.MAP_DISPLAY_WIDTH][GUIConstants.MAP_DISPLAY_HEIGHT];
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        BufferedImage[] mapTiles = TileSet.getMapTiles();
        BufferedImage[] terminalTiles = TileSet.getTerminalTiles();
        Font stringFont = TileSet.getStringFont();
        int tileSize = TileSet.getTileSize();
        if(mapTiles  == null || terminalTiles == null)
            return;
        Graphics2D g2d = (Graphics2D)g;
        
        paintStandardBorders(g2d, mapTiles, tileSize);
        paintMapArea(g2d, mapTiles, tileSize);
        
        MessagePanel.paint(g2d, terminalTiles, tileSize);
        
    }
    
    private void paintMapArea(Graphics2D g2d, BufferedImage[] mapTiles, int tileSize)
    {        
        Actor player = GameObj.getPlayer();
        GameMap map = GameObj.getMap();
        if(player == null || map == null)
            return;
            
        int xCorner = player.getLoc().x - (GUIConstants.MAP_DISPLAY_WIDTH / 2);
        int yCorner = player.getLoc().y - (GUIConstants.MAP_DISPLAY_HEIGHT / 2);
        
        // load ground tiles
        for(int x = 0; x < GUIConstants.MAP_DISPLAY_WIDTH; x++)
        for(int y = 0; y < GUIConstants.MAP_DISPLAY_HEIGHT; y++)
        {
            if(PlayerFoV.canSee(x + xCorner, y + yCorner))
                mapAreaArray[x][y] = map.getCell(x + xCorner, y + yCorner).getImage();
            else
            {
                mapAreaArray[x][y] = map.getCell(x + xCorner, y + yCorner).getOOVImage();
            }
        }
        
        // load items
        
        // load actors
        Vector<Actor> actorList = GameObj.getActorList();
        for(int i = 0; i < actorList.size(); i++)
        {
            Actor actor = actorList.elementAt(i);
            if(PlayerFoV.canSee(actor.getLoc()))
            {
                mapAreaArray[actor.getLoc().x - xCorner][actor.getLoc().y - yCorner] = actor.getImage();
            }
        }
        
        // paint area
        for(int x = 0; x < GUIConstants.MAP_DISPLAY_WIDTH; x++)
        for(int y = 0; y < GUIConstants.MAP_DISPLAY_HEIGHT; y++)
        {
            BufferedImage curImage = mapAreaArray[x][y];
            g2d.drawImage(curImage, (x + GUIConstants.MAP_DISPLAY_ORIGIN[0]) * tileSize, 
                                    (y + GUIConstants.MAP_DISPLAY_ORIGIN[1]) * tileSize, null);
        }
    }
    
    private void paintStandardBorders(Graphics2D g2d, BufferedImage[] mapTiles, int tileSize)
    {
        // tile indexes
        int vert = 3 + (11 * 16);
        int horiz = 4 + (12 * 16);
        int doubleHoriz = 13 + (12 * 16);
        int doubleVert = 10 + (11 * 16);
        int uRCorner = (12 * 16) - 1;
        int lLCorner = uRCorner + 1;
        int lRCorner = 9 + (13 * 16);
        int uLCorner = lRCorner + 1;
        int junctA = 2 + (13 * 16);
        int junctB = 9 + (11 * 16);
        int junctC = (13 * 16);
        int junctD = 11 + (12 * 16);
        int junctE = 6 + (12 * 16);
        
        // draw screen borders
        int bottomWall = (GUIConstants.TILES_PER_SCREEN[1] - 1) * tileSize;
        int rightWall = (GUIConstants.TILES_PER_SCREEN[0] - 1) * tileSize;
        for(int x = 1; x < GUIConstants.TILES_PER_SCREEN[0] - 1; x++)
        {
            g2d.drawImage(mapTiles[horiz], x * tileSize, 0, null);
            g2d.drawImage(mapTiles[horiz], x * tileSize, bottomWall, null);
        }
        for(int y = 1; y < GUIConstants.TILES_PER_SCREEN[1] - 1; y++)
        {
            g2d.drawImage(mapTiles[vert], 0, y * tileSize, null);
            g2d.drawImage(mapTiles[vert], rightWall, y * tileSize, null);
        }
        
        // message area
        rightWall = GUIConstants.MESSAGE_DISPLAY_ORIGIN[0] + GUIConstants.MESSAGE_DISPLAY_WIDTH;
        bottomWall = GUIConstants.MESSAGE_DISPLAY_ORIGIN[1] + GUIConstants.MESSAGE_DISPLAY_HEIGHT;
        for(int x = GUIConstants.MESSAGE_DISPLAY_ORIGIN[0]; x < rightWall; x++)
            g2d.drawImage(mapTiles[doubleHoriz], x * tileSize, bottomWall * tileSize, null);
        for(int y = GUIConstants.MESSAGE_DISPLAY_ORIGIN[1]; y < bottomWall; y++)
            g2d.drawImage(mapTiles[doubleVert], rightWall * tileSize, y * tileSize, null);
        
        // player info area
        rightWall = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[0] + GUIConstants.PLAYER_INFO_DISPLAY_WIDTH;
        bottomWall = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1] + GUIConstants.PLAYER_INFO_DISPLAY_HEIGHT;
        for(int y = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1]; y < bottomWall; y++)
            g2d.drawImage(mapTiles[doubleVert], rightWall * tileSize, y * tileSize, null);
        
        // map area
        rightWall = GUIConstants.MAP_DISPLAY_ORIGIN[0] + GUIConstants.MAP_DISPLAY_WIDTH;
        bottomWall = GUIConstants.MAP_DISPLAY_ORIGIN[1] + GUIConstants.MAP_DISPLAY_HEIGHT;
        for(int y = GUIConstants.MAP_DISPLAY_ORIGIN[1]; y < bottomWall; y++)
            g2d.drawImage(mapTiles[doubleVert], rightWall * tileSize, y * tileSize, null);
            
        // HUD area outlined by induction
        
        // corners
        g2d.drawImage(mapTiles[uLCorner], 0, 0, null);
        g2d.drawImage(mapTiles[uRCorner], (GUIConstants.TILES_PER_SCREEN[0] - 1) * tileSize, 0, null);
        g2d.drawImage(mapTiles[lLCorner], 0, (GUIConstants.TILES_PER_SCREEN[1] - 1) * tileSize, null);
        g2d.drawImage(mapTiles[lRCorner], (GUIConstants.TILES_PER_SCREEN[0] - 1) * tileSize, 
                                          (GUIConstants.TILES_PER_SCREEN[1] - 1) * tileSize, null);
                                          
        int v1 = (GUIConstants.MAP_DISPLAY_ORIGIN[0] - 1) * tileSize;
        int v2 = (GUIConstants.HUD_DISPLAY_ORIGIN[0] - 1) * tileSize;
        int h1 = (GUIConstants.MAP_DISPLAY_ORIGIN[1] - 1) * tileSize;
        int h2 = (GUIConstants.TILES_PER_SCREEN[1] - 1) * tileSize;
        g2d.drawImage(mapTiles[junctA], v2, 0, null);
        g2d.drawImage(mapTiles[junctB], v2, h1, null);
        g2d.drawImage(mapTiles[junctC], v2, h2, null);
        g2d.drawImage(mapTiles[junctC], v1, h2, null);
        g2d.drawImage(mapTiles[junctD], v1, h1, null);
        g2d.drawImage(mapTiles[junctE], 0, h1, null);
    }
    
}