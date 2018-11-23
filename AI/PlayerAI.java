package AI;

import Actor.*;
import Ability.*;

public class PlayerAI extends AIBase
{
    public PlayerAI(Actor a)
    {
        super(a);
        setTeam(Team.HERO);
    }
    
    @Override
    public void plan()
    {
        if(getPendingAction() == null)
            setPendingAction(Action.CONTEXTUAL);
    }
    
    @Override
    public void clear()
    {
        pendingAction = Action.CONTEXTUAL;
        pendingCoord = null;
        pendingIndex = -1;
    }
}