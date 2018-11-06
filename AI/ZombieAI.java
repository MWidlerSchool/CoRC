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
                Direction dirTo = Direction.getDirectionTo(self.getLoc(), target.getLoc());
                Coord step = self.getLoc();
                step.add(dirTo.getAsCoord());
                
                // can step directly towards
                if(self.canStep(step))
                {
                    setPendingAction(Action.STEP);
                    setPendingCoord(step);
                }
                
                // nearest step blocked
                if(hasPlan() == false)
                {
                    Direction option2 = dirTo.getNextClockwise();
                    Direction option3 = dirTo.getNextCounterclockwise();
                    if(RNG.nextBoolean())
                    {
                        option2 = dirTo.getNextCounterclockwise();
                        option3 = dirTo.getNextClockwise();
                    }
                    step = self.getLoc();
                    step.add(option2.getAsCoord());
                    if(self.canStep(step) == false)
                    {
                        step = self.getLoc();
                        step.add(option3.getAsCoord());
                        if(self.canStep(step) == false)
                        {
                            step = null;
                        }
                    }
                    if(step != null)
                    {
                        setPendingAction(Action.STEP);
                        setPendingCoord(step);
                    }
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