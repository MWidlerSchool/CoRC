package AI;

import Actor.*;
import Map.*;
import MyTools.*;
import Engine.*;
import Ability.*;
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
            case STEP           :   step(pendingCoord);
                                    break;
            case WAIT           :   delay();
                                    break;
            case BASIC_ATTACK   :   attack(self.getBasicAttack(), pendingCoord);
                                    break;
            case USE            :   use(pendingCoord);
                                    break;
            case PICK_UP        :   pickUp(pendingCoord);
                                    break;
            case CONTEXTUAL     :   interpertContext();
                                    if(hasPlan())
                                        act();
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
    
    public void use(Coord target)
    {
        if(GameObj.getMap().getCell(target) instanceof Usable)
        {
            Usable cell = (Usable)GameObj.getMap().getCell(target);
            cell.use(self);
        }
            
        self.dischargeAction();
    }
    
    public void attack(Attack attack, Coord targetLoc)
    {
        Actor target = GameObj.getActorAt(targetLoc);
        if(target == null)
        {
            clear();
            return;
        }
        
        CombatManager.resolveAttack(self, target, attack);
            
        self.dischargeAction();
    }
    
    public void pickUp(Coord target)
    {
        if(GameObj.getMap().getCell(target).hasItem() == false)
        {
            MessagePanel.add("Nothing to pick up here . . .");
            return;
        }
        if(self.getInventory().isFull())
        {
            MessagePanel.add("Your inventory is already full!");
            return;
        }
        self.getInventory().add(GameObj.getMap().getCell(target).takeItem());
        self.dischargeMove();
    }
    
    public void interpertContext()
    {
        Direction dir = Direction.getDirectionTo(self.getLoc(), getPendingCoord());
        Coord targetLoc = self.getLoc();
        targetLoc.add(dir.getAsCoord());
        
        if(self.getLoc().equals(targetLoc))
        {
            setPendingAction(Action.WAIT);
        }
        else if(self.canStep(targetLoc))
        {
            setPendingAction(Action.STEP);
        }
        else if(GameObj.isActorAt(targetLoc))
        {
            setPendingAction(Action.BASIC_ATTACK);
        }
        else if(GameObj.getMap().getCell(targetLoc) instanceof Usable)
        {
            setPendingAction(Action.USE);
        }
        
        // no legal action found
        if(getPendingAction() == Action.CONTEXTUAL)
        {
            clear();
        }
    }
}