package Engine;

import java.util.*;

public class InitManager implements Runnable
{
    private Thread thread;
    private Vector<InitObj> initList;
    private int initIndex;
    private static boolean keepRunning;
    
    public Vector<InitObj> getInitList(){return initList;}
    
    public InitManager()
    {
        initList = new Vector<InitObj>();
        reset();
    }
    
    public void reset()
    {
        initList = new Vector<InitObj>();
        initIndex = 0;
        thread = new Thread(this);
        thread.start();
        keepRunning = true;
    }
    
    public void run()
    {
        while(keepRunning)
        {
            if(initList.size() == 0 || GameObj.masterHold())
                continue;
            
            InitObj curObj = initList.elementAt(initIndex);
            // charge element if it's not the player thinking while charged
            if(curObj.isCharged() == false)
                curObj.charge();
            
            // take turn if charged
            if(curObj.isCharged())
            {
                curObj.act();
            }
            
            // go to next, unless it's the player thinking while charged
            if(curObj.isCharged() == false)
            {
                initIndex++;
                if(initIndex >= initList.size())
                    initIndex = 0;
            }
        }
    }
    

    
    public void add(InitObj obj)
    {
        initList.add(obj);
    }
    
    public void remove(InitObj obj)
    {
        for(int i = 0; i < initList.size(); i++)
        {
            if(initList.elementAt(i) == obj)
            {
                boolean underIndex = i <= initIndex;
                initList.removeElementAt(i);
                if(underIndex)
                {
                    i--;
                    initIndex--;
                }
            }
        }
    }

}