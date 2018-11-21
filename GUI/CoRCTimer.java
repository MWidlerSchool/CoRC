package GUI;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import Engine.*;

public class CoRCTimer implements ActionListener
{
    private javax.swing.Timer timer;
    private static Vector<ActionListener> listenerList = new Vector<ActionListener>();
    private static int turnDelay = 0;
    
    public static void setTurnDelay(int td){turnDelay = td;}
    public static int getTurnDelay(){return turnDelay;}
    
    public CoRCTimer(int speed)
    {
        turnDelay = 0;
        listenerList.clear();
        timer = new javax.swing.Timer(speed, this);
        timer.start();
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        for(int i = 0; i < listenerList.size(); i++)
        {
            listenerList.elementAt(i).actionPerformed(ae);
        }
        
        if(turnDelay > 0)
        {
            turnDelay--;
            if(turnDelay == 0)
                InitManager.setTurnPause(false);
        }
    }
    
    public static void add(ActionListener listener)
    {
        listenerList.add(listener);
    }
}