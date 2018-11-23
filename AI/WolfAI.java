package AI;

import Actor.*;
import MyTools.*;
import Engine.*;
import GUI.*;
import java.util.*;

/*
    Intelligently path towards player, dumbstep if can't, melee attack if able.
*/
public class WolfAI extends ZombieAI
{
    public WolfAI(Actor s)
    {
        super(s);
    }
    
    @Override
    public void plan()
    {
        Actor target = getMemory().getNearestVisibleEnemy();
        
        // can see target
        if(target != null && self.canSee(target))
        {
            // adjacent
            if(EngineTools.isAdjacent(self.getLoc(), target.getLoc()))
            {
                setPendingAction(Action.BASIC_ATTACK);
                setPendingCoord(target.getLoc());
            }
            else // not adjacent
            {
                Vector<Coord> path = getPathTo(target.getLoc());
                // zombie path if no intelligent path
                if(path == null || path.size() == 0)
                {
                    super.plan();
                }
                else
                {   // set step on path
                    setPendingAction(Action.STEP);
                    setPendingCoord(path.elementAt(0));
                }
            }
        }
        
        // move towards closest remembered enemy
        if(hasPlan() == false)
        {
            Coord lastRemembered = getMemory().getNearestRememberedEnemyLoc();
            if(lastRemembered != null)
            {
                Vector<Coord> path = getPathTo(lastRemembered);
                // zombie path if no intelligent path
                if(path == null || path.size() == 0)
                {
                    Coord step = getStepTowards(lastRemembered);
                    if(step != null)
                    {
                        setPendingAction(Action.STEP);
                        setPendingCoord(step);
                    }
                }
                else
                {   // set step on path
                    setPendingAction(Action.STEP);
                    setPendingCoord(path.elementAt(0));
                }
            }
        }
        
        // catchall at end
        if(hasPlan() == false)
        {
            setPendingAction(Action.WAIT);
            setPendingCoord(self.getLoc());
        }
    }
}