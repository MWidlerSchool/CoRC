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
        Actor target = GameObj.getPlayer();
        // can see target
        if(self.canSee(target))
        {
            // adjacent
            if(MathTools.getAngbandMetric(self.getLoc(), target.getLoc()) == 1)
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
        
        // catchall at end
        if(hasPlan() == false)
        {
            setPendingAction(Action.WAIT);
            setPendingCoord(self.getLoc());
        }
    }
}