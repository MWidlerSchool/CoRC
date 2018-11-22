package GUI;

import java.awt.event.*;
import java.awt.*;
import MyTools.*;
import Engine.*;
import Actor.*;
import AI.*;

/*
    Forwards key and mouse input to the proper place.
*/

public class InputListener implements KeyListener, MouseListener, MouseMotionListener, GUIConstants
{
    private static Coord mouseLoc = new Coord();    // in tiles
    
    public void keyPressed(KeyEvent ke){testInput(ke);}
    public void keyReleased(KeyEvent ke){}
    public void keyTyped(KeyEvent ke){}
    
    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}
    public void mouseClicked(MouseEvent me){}
    
    public void mouseEntered(MouseEvent me){}
    public void mouseExited(MouseEvent me){}
    public void mouseDragged(MouseEvent me){}
    
    public void mouseMoved(MouseEvent me)
    {
        mouseLoc.x = me.getX() / CoRCFrame.getTileSize();
        mouseLoc.y = me.getY() / CoRCFrame.getTileSize();
    }
    
    
    
    public void testInput(KeyEvent ke)
    {
        
        Coord loc = GameObj.getPlayer().getLoc();
        switch(ke.getKeyCode())
        {
            case KeyEvent.VK_NUMPAD1    :  loc.x -= 1;
                                           loc.y += 1;
                                           GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_NUMPAD2    :  loc.y += 1;
                                           GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_NUMPAD3    :  loc.x += 1;
                                           loc.y += 1;
                                           GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_NUMPAD4    :  loc.x -= 1;
                                           GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_NUMPAD5    :  GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_NUMPAD6    :  loc.x += 1;
                                           GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_NUMPAD7    :  loc.x -= 1;
                                           loc.y -= 1;
                                           GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_NUMPAD8    :  loc.y -= 1;
                                           GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_NUMPAD9    :  loc.x += 1;
                                           loc.y -= 1;
                                           GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_U          :  GameObj.getPlayer().getAI().setPendingAction(Action.USE);
                                           break;
            case KeyEvent.VK_G          :  GameObj.getPlayer().getAI().setPendingAction(Action.PICK_UP);
                                           GameObj.getPlayer().getAI().setPendingCoord(loc);
                                           break;
            case KeyEvent.VK_I          :  if(MainPanel.getDisplayState() != INVENTORY_DISPLAY)
                                               MainPanel.setDisplayState(INVENTORY_DISPLAY);
                                           else
                                               MainPanel.setDisplayState(MAIN_GAME_DISPLAY);
                                           break;
            case KeyEvent.VK_H          :  if(MainPanel.getDisplayState() != HELP_DISPLAY)
                                               MainPanel.setDisplayState(HELP_DISPLAY);
                                           else
                                               MainPanel.setDisplayState(MAIN_GAME_DISPLAY);
                                           break;
            case KeyEvent.VK_P          :  if(MainPanel.getDisplayState() != PREFERENCE_DISPLAY)
                                               MainPanel.setDisplayState(PREFERENCE_DISPLAY);
                                           else
                                               MainPanel.setDisplayState(MAIN_GAME_DISPLAY);
                                           break;
            case KeyEvent.VK_ESCAPE     :  if(MainPanel.getDisplayState() != MAIN_GAME_DISPLAY)
                                                MainPanel.setDisplayState(MAIN_GAME_DISPLAY);
                                           break;
            case KeyEvent.VK_SPACE      :  //MessagePanel.add("mouseLoc = " + mouseLoc.toString());
                                           //GameObj.getPlayer().getResourceBlock().setCurHealth(GameObj.getPlayer().getResourceBlock().getCurHealth() - 1);
                                           //MessagePanel.add("Player inventory size = " + GameObj.getPlayer().getInventory().size());
                                           VisualEffectsManager.setScreenShake(5);
                                           for(int i = 0; i < 10; i++)
                                               VisualEffectsManager.add(VisualEffect.getTestEffect(GameObj.getPlayer().getLoc()));
                                           return;
        }
    }
}