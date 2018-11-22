package GUI;

import javax.swing.JFrame;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import Engine.*;
import Actor.*;
import MyTools.*;

public class CoRCFrame extends JFrame implements ActionListener, GUIConstants
{
    private static int tileSize = GUIConstants.DEFAULT_TILE_SIZE;
    private MainPanel mainPanel;
    private InputListener inputListener;
    public InitManager initManager;
    public CoRCTimer timer;
    public VisualEffectsManager veManager;
    
    public CoRCFrame()
    {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);   // must come after setResizable and before getInsets()
        Insets insets = this.getInsets();
        int width = TILES_PER_SCREEN[0] * tileSize;
        int height = TILES_PER_SCREEN[1] * tileSize;
        width += insets.left + insets.right;
        height += insets.bottom + insets.top;
        setSize(tileSize);
        setSize(width, height);
        setTitle(TITLE_STRING);
        mainPanel = new MainPanel(this);
        FontManager.loadFonts();
        
        inputListener = new InputListener();
        addKeyListener(inputListener);
        addMouseListener(inputListener);
        addMouseMotionListener(inputListener);
        timer = new CoRCTimer(TIMER_SPEED);
        timer.add(this);
        
        initManager = new InitManager();
        veManager = new VisualEffectsManager();
        
        GameObj.init(timer);
        GameObj.resume();
    }
    
    public static int getTileSize()
    {
        return tileSize;
    }
    
    public static void setSize(int s)
    {
        tileSize = s;
        ScreenPanel.setTileSize(s);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(MainPanel.getDisplayState() == MAIN_GAME_DISPLAY)
        {
            PlayerFoV.softUpdate();
            veManager.update();
        }
        mainPanel.repaint();
    }
    
    
    public static void main(String[] args)
    {
        CoRCFrame frame = new CoRCFrame();
    }
    

}