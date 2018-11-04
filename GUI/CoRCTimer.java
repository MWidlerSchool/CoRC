package GUI;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class CoRCTimer implements ActionListener
{
    private javax.swing.Timer timer;
    private static Vector<ActionListener> listenerList = new Vector<ActionListener>();
    
    public CoRCTimer(int speed)
    {
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
    }
    
    public static void add(ActionListener listener)
    {
        listenerList.add(listener);
    }
}