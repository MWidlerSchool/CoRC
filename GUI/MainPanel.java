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
    private Font mapFont = null;
    private Font terminalFont = null;
    private Font stringFont = null;
    private int tileSize = -1;
    private int displayState = GUIConstants.MAIN_GAME_DISPLAY_STATE;

	public JFrame getParentFrame(){return parentFrame;}
    

    public MainPanel(JFrame parent)
    {
        super();
        parentFrame = parent;
        parentFrame.add(this);
        setFocusable(false);
        setBackground(Color.BLACK);
        StagePanel.init();
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
        
        if(displayState == GUIConstants.MAIN_GAME_DISPLAY_STATE)
        {
            paintStandardBorders(g2d, mapFont);
            MessagePanel.paint(g2d, stringFont);
            PlayerInfoPanel.paint(g2d, stringFont, terminalFont);
            StagePanel.paint(g2d, mapFont);
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