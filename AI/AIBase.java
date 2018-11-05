package AI;

import Actor.*;
import Map.*;
import MyTools.*;
import Engine.*;
import GUI.MessagePanel;

public class AIBase
{
    protected Actor self;
    protected Action pendingAction;
	protected Coord pendingCoord;
	protected int pendingIndex;


	public Action getPendingAction(){return pendingAction;}
	public Coord getPendingCoord(){return new Coord(pendingCoord);}
	public int getPendingIndex(){return pendingIndex;}


	public void setPendingAction(Action p){pendingAction = p;}
	public void setPendingCoord(Coord p){setPendingCoord(p.x, p.y);}
	public void setPendingCoord(int x, int y){pendingCoord = new Coord(x, y);}
	public void setPendingIndex(int p){pendingIndex = p;}

    public AIBase(Actor a)
    {
        self = a;
        pendingAction = null;
        pendingCoord = null;
        pendingIndex = -1;
    }
    
    public void clear()
    {
        pendingAction = null;
        pendingCoord = null;
        pendingIndex = -1;
    }
    
    public boolean hasPlan()
    {
        if(pendingAction == null || pendingCoord == null)
            return false;
        return true;
    }
    
    
    // overridden in child classes
    public void plan()
    {
        if(self != GameObj.getPlayer())
        {
            pendingAction = Action.WAIT;
            pendingCoord = self.getLoc();
        }
    }
    
    public void act()
    {
        switch(pendingAction)
        {
            case STEP   :   step(pendingCoord);
                            break;
            case WAIT   :   delay();
                            break;
        }
        clear();
    }
    
    // wait for the fastest speed the actor has
    public void delay()
    {
        if(self.getStatBlock().getMoveSpeed() < self.getStatBlock().getActionSpeed())
            self.dischargeMove();
        else
            self.dischargeAction();
    }
    
    public void step(Coord target)
    {
        Direction dir = Direction.getDirectionTo(self.getLoc(), target);
        Coord targetLoc = self.getLoc();
        targetLoc.add(dir.getAsCoord());
        
        if(self.canStep(targetLoc))
            self.setLoc(targetLoc);
            
        self.dischargeMove();
    }
}