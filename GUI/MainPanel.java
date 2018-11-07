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
        for(int x = 0; x < GUIConstants.MAP_DISPLAY_WIDTH; x++)
        for(int y = 0; y < GUIConstants.MAP_DISPLAY_HEIGHT; y++)
        {
            ScreenObj curImage = mapAreaArray[x][y];
            if(mapColorArray[x][y] == null)
                g2d.setColor(GUIConstants.OOB_COLOR);
            else
                g2d.setColor(mapColorArray[x][y]);
            g2d.drawString(curImage.getStr(), (x + GUIConstants.MAP_DISPLAY_ORIGIN[0]) * tileSize, 
                                     (y + GUIConstants.MAP_DISPLAY_ORIGIN[1]) * tileSize);
        }
    }
    
    private void paintStandardBorders(Graphics2D g2d, Font mapFont)
    {
        g2d.setFont(mapFont);
        g2d.setColor(GUIConstants.WHITE);
        // draw screen borders
        int bottomWall = (GUIConstants.TILES_PER_SCREEN[1]) * tileSize;
        int rightWall = (GUIConstants.TILES_PER_SCREEN[0] - 1) * tileSize;
        for(int x = 1; x < GUIConstants.TILES_PER_SCREEN[0] - 1; x++)
        {
            g2d.drawString("+", x * tileSize, tileSize);
            g2d.drawString("+", x * tileSize, bottomWall);
        }
        for(int y = 1; y <= GUIConstants.TILES_PER_SCREEN[1]; y++)
        {
            g2d.drawString("+", 0, y * tileSize);
            g2d.drawString("+", rightWall, y * tileSize);
        }
        
        // message area
        rightWall = GUIConstants.MESSAGE_DISPLAY_ORIGIN[0] + GUIConstants.MESSAGE_DISPLAY_WIDTH;
        bottomWall = GUIConstants.MESSAGE_DISPLAY_ORIGIN[1] + GUIConstants.MESSAGE_DISPLAY_HEIGHT;
        for(int x = GUIConstants.MESSAGE_DISPLAY_ORIGIN[0]; x < rightWall; x++)
            g2d.drawString("+", x * tileSize, bottomWall * tileSize);
        for(int y = GUIConstants.MESSAGE_DISPLAY_ORIGIN[1]; y <= bottomWall; y++)
            g2d.drawString("+", rightWall * tileSize, y * tileSize);
        
        // player info area
        rightWall = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[0] + GUIConstants.PLAYER_INFO_DISPLAY_WIDTH;
        bottomWall = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1] + GUIConstants.PLAYER_INFO_DISPLAY_HEIGHT;
        for(int y = GUIConstants.PLAYER_INFO_DISPLAY_ORIGIN[1]; y <= bottomWall; y++)
            g2d.drawString("+", rightWall * tileSize, y * tileSize);
        
        // map area
        rightWall = GUIConstants.MAP_DISPLAY_ORIGIN[0] + GUIConstants.MAP_DISPLAY_WIDTH;
        bottomWall = GUIConstants.MAP_DISPLAY_ORIGIN[1] + GUIConstants.MAP_DISPLAY_HEIGHT;
        for(int y = GUIConstants.MAP_DISPLAY_ORIGIN[1]; y <= bottomWall; y++)
            g2d.drawString("+", rightWall * tileSize, y * tileSize);
            
        // HUD area outlined by induction
    }
    
}