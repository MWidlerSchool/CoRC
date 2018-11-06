package GUI;

import javax.swing.JFrame;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import Engine.*;
import Actor.*;
import MyTools.*;

public class CoRCFrame extends JFrame implements ActionListener
{
    private static int tileSize = GUIConstants.DEFAULT_TILE_SIZE;
    private MainPanel mainPanel;
    private InputListener inputListener;
    public InitManager initManager;
    public CoRCTimer timer;
    
    public CoRCFrame()
    {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);   // must come after setResizable and before getInsets()
        Insets insets = this.getInsets();
        int width = GUIConstants.TILES_PER_SCREEN[0] * tileSize;
        int height = GUIConstants.TILES_PER_SCREEN[1] * tileSize;
        width += insets.left + insets.right;
        height += insets.bottom + insets.top;
        setSize(width, height);
        setTitle(GUIConstants.TITLE_STRING);
        mainPanel = new MainPanel(this);
        ImageLoader.loadFont();
        TileSet.set(tileSize);
        
        inputListener = new InputListener();
        addKeyListener(inputListener);
        addMouseListener(inputListener);
        addMouseMotionListener(inputListener);
        timer = new CoRCTimer(1000 / 30);
        timer.add(this);
        
        GameObj.init(timer);
        
        initManager = new InitManager();
        
        
        GameObj.resume();
    }
    
    public static int getTileSize()
    {
        return tileSize;
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        PlayerFoV.softUpdate();
        mainPanel.repaint();
    }
    
    public static void main(String[] args)
    {
        CoRCFrame frame = new CoRCFrame();
    }
    

}