package Engine;

import Actor.*;
import MyTools.*;
import GUI.*;
import java.util.*;

public class ActorLocManager
{
    private static Actor[][] locTable = null;
    
    public static void init(int w, int h)
    {
        locTable = new Actor[w][h];
        for(int x = 0; x < w; x++)
        for(int y = 0; y < h; y++)
        {
            locTable[x][y] = null;
        }
    }
    
    public static void set(Vector<Actor> aList)
    {
        for(int i = 0; i < aList.size(); i++)
        {
            add(aList.elementAt(i));
        }
    }
    
    public static void add(Actor a)
    {
        locTable[a.getLoc().x][a.getLoc().y] = a;
    }
    
    public static void remove(Actor a)
    {
        locTable[a.getLoc().x][a.getLoc().y] = null;
    }
    
    public static void update(Actor a, Coord was, Coord is)
    {
        if(locTable == null)
            return;
            
        if(locTable[was.x][was.y] == a && locTable[is.x][is.y] == null)
        {
            locTable[was.x][was.y] = null;
            locTable[is.x][is.y] = a;
        }
        else
        {
            boolean oldEmpty = false;
            boolean newFull = false;
            if(locTable[was.x][was.y] != a)
                MessagePanel.add("Error in ActorLocManager: actor not at oldLoc.");
            if(locTable[is.x][is.y] != null)
                MessagePanel.add("Error in ActorLocManager: newLoc inhabited.");
        }
    }
    
    public static Actor getActorAt(Coord c){return getActorAt(c.x, c.y);}
    public static Actor getActorAt(int x, int y)
    {
        return locTable[x][y];
    }
}