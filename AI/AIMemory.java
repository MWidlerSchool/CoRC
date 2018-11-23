package AI;

import Actor.*;
import Engine.*;
import MyTools.*;
import java.util.*;

public class AIMemory implements AIConstants
{
	private Actor self;
	private Vector<MemoryObj> list;
    private int length;


	public Actor getSelf(){return self;}
	public Vector<MemoryObj> getList(){return list;}
    public int getLength(){return length;}


	public void setSelf(Actor s){self = s;}
	public void setList(Vector<MemoryObj> l){list = l;}
    public void setLength(int l){length = l;}
    
    public AIMemory(Actor a)
    {
        self = a;
        list = new Vector<MemoryObj>();
        length = DEFAULT_MEMORY_LENGTH;
    }
    
    // update information on who it can see
    public void update(Vector<Actor> actorList)
    {
        // update who it can see
        for(int i = 0; i < actorList.size(); i++)
        {
            if(self.canSee(actorList.elementAt(i)) && self != actorList.elementAt(i))
            {
                updateEntry(actorList.elementAt(i));
            }
        }
        // increment and remove ones it can't
        for(int i = 0; i < list.size(); i++)
        {
            MemoryObj memory = list.elementAt(i);
            if(self.canSee(memory.actor) == false)
            {
                memory.lastSeen += 1;
                if(memory.lastSeen > length)
                {
                    list.removeElementAt(i);
                    i--;
                }
            }
        }
    }
    
    public void updateEntry(Actor visibleActor)
    {
        boolean found = false;
        for(int i = 0; i < list.size(); i++)
        {
            if(list.elementAt(i).actor == visibleActor)
            {
                found = true;
                list.elementAt(i).update(visibleActor.getLoc());
                break;
            }   
        }
        if(!found)
        {
            list.add(new MemoryObj(visibleActor));
        }
    }
    
    public Coord getLastSeen(Actor a)
    {
        Coord loc = null;
        for(int i = 0; i < list.size(); i++)
        {
            if(list.elementAt(i).actor == a)
            {
                loc = new Coord(list.elementAt(i).lastSeenAt);
                break;
            }
        }
        return loc;
    }
    
    public Actor getNearestVisibleEnemy()
    {
        int curDist = 10000000;
        Actor closest = null;
        Actor prospect;
        for(int i = 0; i < list.size(); i++)
        {
            prospect = list.elementAt(i).actor;
            if(self.isHostile(prospect) && list.elementAt(i).lastSeen == 0)
            {
                int prospectDist = MathTools.getDistanceMetric(self.getLoc(), prospect.getLoc());
                if(prospectDist < curDist)
                {
                    curDist = prospectDist;
                    closest = prospect;
                }
            }
        }
        return closest;
    }
    
    public Coord getNearestRememberedEnemyLoc()
    {
        int curDist = 10000000;
        Coord closest = null;
        Coord prospect;
        for(int i = 0; i < list.size(); i++)
        {
            prospect = list.elementAt(i).lastSeenAt;
            if(self.isHostile(list.elementAt(i).actor) && list.elementAt(i).lastSeen > 0)
            {
                int prospectDist = MathTools.getDistanceMetric(self.getLoc(), prospect);
                if(prospectDist < curDist)
                {
                    curDist = prospectDist;
                    closest = prospect;
                }
            }
        }
        return closest;
    }
    
    public void alertFriends()
    {
        Vector<Actor> friendList = new Vector<Actor>();
        Vector<Actor> enemyList = new Vector<Actor>();
        
        for(int i = 0; i < list.size(); i++)
        {
            Actor a = list.elementAt(i).actor;
            if(self.canSee(a))
            {
                if(self.isHostile(a))
                    enemyList.add(a);
                else if(self.isFriendly(a))
                    friendList.add(a);
            }
        }
        for(int f = 0; f < friendList.size(); f++)
        for(int e = 0; e < enemyList.size(); e++)
            friendList.elementAt(f).alert(enemyList.elementAt(e));
    }


    private class MemoryObj
    {
        public Actor actor;
        public int lastSeen;
        public Coord lastSeenAt;
        
        public MemoryObj(Actor a)
        {
            actor = a;
            lastSeen = 0;
            lastSeenAt = actor.getLoc();
        }
        
        public void update(Coord c)
        {
            lastSeen = 0;
            lastSeenAt = c;
        }
    }
}