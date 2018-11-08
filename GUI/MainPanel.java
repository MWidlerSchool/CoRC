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
    private ScreenObj[][] mapAreaArray;
    private Color[][] mapColorArray;
    private Font mapFont = null;
    private Font terminalFont = null;
    private Font stringFont = null;
    private int tileSize = -1;

	public JFrame getParentFrame(){return parentFrame;}
    

    public MainPanel(JFrame parent)
    {
        super();
        parentFrame = parent;
        parentFrame.add(this);
        setFocusable(false);
        setBackground(Color.BLACK);
        mapAreaArray = new ScreenObj[GUIConstants.MAP_DISPLAY_WIDTH][GUIConstants.MAP_DISPLAY_HEIGHT];
        mapColorArray = new Color[GUIConstants.MAP_DISPLAY_WIDTH][GUIConstants.MAP_DISPLAY_HEIGHT];

    }
    
    public void setFonts()
    {
        tileSize = CoRCFrame.getTileSize();
        mapFont = FontManager.getMapFont(tileSize);
        terminalFont = FontManager.getTerminalFont(tileSize);
        stringFont = FontManager.getStringFont(tileSize);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        if(mapFont  == null || terminalFont == null)
        {
            setFonts();
            if(mapFont  == null || terminalFont == null)
                return;
        }
            
        Graphics2D g2d = (Graphics2D)g;
        
        paintStandardBorders(g2d, mapFont);
        paintMapArea(g2d, mapFont);
        
        MessagePanel.paint(g2d, stringFont);
        PlayerInfoPanel.paint(g2d, stringFont, terminalFont);
        
    }
    
    private void paintMapArea(Graphics2D g2d, Font mapFont)
    {        
        g2d.setFont(mapFont);
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
            mapAreaArray[x][y] = map.getCell(x + xCorner, y + yCorner);
            if(PlayerFoV.canSee(x + xCorner, y + yCorner))
                mapColorArray[x][y] = map.getCell(x + xCorner, y + yCorner).getFGColor();
            else
                mapColorArray[x][y] = GUIConstants.OOB_COLOR;
        }
        
        // load items
        
        // load actors
        Vector<Actor> actorList = GameObj.getActorList();
        for(int i = 0; i < actorList.size(); i++)
        {
            Actor actor = actorList.elementAt(i);
            if(PlayerFoV.canSee(actor.getLoc()))
            {
                mapAreaArray[actor.getLoc().x - xCorner][actor.getLoc().y - yCorner] = actor;
                mapColorArray[actor.getLoc().x - xCorner][actor.getLoc().y - yCorner] = actor.getFGColor();
            }
        }
        
        // paint area
        int xOff = 0;
        int yOff = 0;
        for(int x = 0; x < GUIConstants.MAP_DISPLAY_WIDTH; x++)
        for(int y = 0; y < GUIConstants.MAP_DISPLAY_HEIGHT; y++)
        {
            ScreenObj curImage = mapAreaArray[x][y];
            if(mapColorArray[x][y] == null)
                g2d.setColor(GUIConstants.OOB_COLOR);
            else
                g2d.setColor(mapColorArray[x][y]);
            // offset for wide tiles
            xOff = 0;
            if(g2d.getFontMetrics().stringWidth(curImage.getStr()) > tileSize)
            {
                xOff = (g2d.getFontMetrics().stringWidth(curImage.getStr()) - tileSize);
            }
            g2d.drawString(curImage.getStr(), ((x + GUIConstants.MAP_DISPLAY_ORIGIN[0]) * tileSize) - xOff, 
                                     (y + GUIConstants.MAP_DISPLAY_ORIGIN[1] + 1) * tileSize);
        }
    }
    
    private void paintStandardBorders(Graphics2D g2d, Font mapFont)
    {
        g2d.setFont(mapFont);
        g2d.setColor(GUIConstants.WHITE);
        
        String vert = Character.toString((char)9474);
        String horiz = Character.toString((char)9472);
        String doubleVert = Character.toString((char)9553);
        String doubleHoriz = Character.toString((char)9552);
        
        // draw screen borders
        int bottomWall = (GUIConstants.TILES_PER_SCREEN[1]) * tileSize;
        int rightWall = (GUIConstants.TILES_PER_SCREEN[0] - 1) * tileSize;
        for(int x = 1; x < GUIConstants.TILES_PER_SCREEN[0] - 1; x++)
        {
            g2d.drawString(horiz, x * tileSize, tileSize);
            g2d.drawString(horiz, x * tileSize, bottomWall);
        }
        for(int y = 2; y <= GUIConstants.TILES_PER_SCREEN[1] - 1; y++)
        {
            g2d.drawString(vert, 0, y * tileSize);
            g2d.drawString(vert, rightWall, y * tileSize);
        }
        
        // corners
        g2d.drawString(Character.toString((char)9484), 0, tileSize);                // upper left
        g2d.drawString(Character.toString((char)9492), 0, bottomWall);              // bottom left
        g2d.drawString(Character.toString((char)9488), rightWall, tileSize);        // upper right
        g2d.drawString(Character.toString((char)9496), rightWall, bottomWall);      // bottom right
        
        // message area
        rightWall = GUIConstants.MESSAGE_DISPLAY_ORIGIN[0] + GUIConstants.MESSAGE_DISPLAY_WIDTH;
        bottomWall = (GUIConstants.MESSAGE_DISPLAY_ORIGIN[1] + 1) + GUIConstants.MESSAGE_DISPLAY_HEIGHT;
        int bottomWallBreak = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[0] + GUIConstants.PLAYER_INFO_DISPLAY_WIDTH;
        
        for(int x = GUIConstants.MESSAGE_DISPLAY_ORIGIN[0]; x < bottomWallBreak; x++)
            g2d.drawString(doubleHoriz, x * tileSize, bottomWall * tileSize);
        for(int x = bottomWallBreak + 1; x < rightWall; x++)
            g2d.drawString(doubleHoriz, x * tileSize, bottomWall * tileSize);
        for(int y = GUIConstants.MESSAGE_DISPLAY_ORIGIN[1] + 1; y < bottomWall; y++)
            g2d.drawString(doubleVert, rightWall * tileSize, y * tileSize);
        
        // player info area
        rightWall = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[0] + GUIConstants.PLAYER_INFO_DISPLAY_WIDTH;
        bottomWall = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1] + GUIConstants.PLAYER_INFO_DISPLAY_HEIGHT;
        for(int y = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1] + 1; y <= bottomWall; y++)
            g2d.drawString(doubleVert, rightWall * tileSize, y * tileSize);
        
        // map area
        rightWall = GUIConstants.MAP_DISPLAY_ORIGIN[0] + GUIConstants.MAP_DISPLAY_WIDTH;
        bottomWall = GUIConstants.MAP_DISPLAY_ORIGIN[1] + GUIConstants.MAP_DISPLAY_HEIGHT;
        for(int y = GUIConstants.MAP_DISPLAY_ORIGIN[1] + 1; y <= bottomWall; y++)
            g2d.drawString(doubleVert, rightWall * tileSize, y * tileSize);
            
        // HUD area outlined by induction
        
        // intersections
        g2d.drawString(Character.toString((char)9573), (GUIConstants.MESSAGE_DISPLAY_ORIGIN[0] + GUIConstants.MESSAGE_DISPLAY_WIDTH) * tileSize, 
                                                        tileSize);
        g2d.drawString(Character.toString((char)9574), (GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[0] + GUIConstants.PLAYER_INFO_DISPLAY_WIDTH) * tileSize, 
                                                        GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1] * tileSize);
        g2d.drawString(Character.toString((char)9571), (GUIConstants.MESSAGE_DISPLAY_ORIGIN[0] + GUIConstants.MESSAGE_DISPLAY_WIDTH) * tileSize, 
                                                        GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1] * tileSize);
        g2d.drawString(Character.toString((char)9576), (GUIConstants.MESSAGE_DISPLAY_ORIGIN[0] + GUIConstants.MESSAGE_DISPLAY_WIDTH) * tileSize, 
                                                       (GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1] + GUIConstants.PLAYER_INFO_DISPLAY_HEIGHT + 1) * tileSize);
        g2d.drawString(Character.toString((char)9576), (GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[0] + GUIConstants.PLAYER_INFO_DISPLAY_WIDTH) * tileSize, 
                                                       (GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1] + GUIConstants.PLAYER_INFO_DISPLAY_HEIGHT + 1) * tileSize);
        g2d.drawString(Character.toString((char)9566),  0, 
                                                       (GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1]) * tileSize);
        
    
    }
    
}