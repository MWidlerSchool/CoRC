package AI;

import Actor.*;
import MyTools.*;
import Engine.*;
import GUI.*;

public class ZombieAI extends AIBase
{
    public ZombieAI(Actor s)
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
                Coord step = getStepTowards(target.getLoc());
                if(step != null)
                {
                    setPendingAction(Action.STEP);
                    setPendingCoord(step);
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