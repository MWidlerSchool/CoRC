package AI;

import Actor.*;
import Map.*;
import MyTools.*;
import Engine.*;
import Ability.*;
import GUI.MessagePanel;
import java.util.*;

public class AIBase implements AIConstants
{
    protected Actor self;
    protected Action pendingAction;
	protected Coord pendingCoord;
	protected int pendingIndex;
    protected Team team;
    protected AIMemory memory;


	public Action getPendingAction(){return pendingAction;}
	public Coord getPendingCoord(){return new Coord(pendingCoord);}
	public int getPendingIndex(){return pendingIndex;}
    public Team getTeam(){return team;}
    public AIMemory getMemory(){return memory;}


	public void setPendingAction(Action p){pendingAction = p;}
	public void setPendingCoord(Coord p){setPendingCoord(p.x, p.y);}
	public void setPendingCoord(int x, int y){pendingCoord = new Coord(x, y);}
	public void setPendingIndex(int p){pendingIndex = p;}
    public void setTeam(Team t){team = t;}
    public void setMemory(AIMemory m){memory = m;}

    public AIBase(Actor a)
    {
        self = a;
        pendingAction = null;
        pendingCoord = null;
        pendingIndex = -1;
        team = Team.VILLAIN;
        memory = new AIMemory(self);
    }
    
    public void clear()
    {
        pendingAction = null;
        pendingCoord = null;
        pendingIndex = -1;
    }
    
    public void beginTurn()
    {
        memory.update(GameObj.getActorList());
        memory.alertFriends();
    }
    
    public boolean hasPlan()
    {
        if(pendingAction == null || pendingCoord == null)
            return false;
        return true;
    }
    
    public boolean isHostile(Actor that)
    {
        return getTeam().isHostile(that.getAI().getTeam());
    }
    
    public boolean isFriendly(Actor that)
    {
        return getTeam().isFriendly(that.getAI().getTeam());
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
            case STEP           :   moveTo(pendingCoord);
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
    
    public void stepTowards(Coord target)
    {
        Direction dir = Direction.getDirectionTo(self.getLoc(), target);
        Coord targetLoc = self.getLoc();
        targetLoc.add(dir.getAsCoord());
        moveTo(targetLoc);
    }
    
    // snaps movement to the selected location, if the actor can step there
    public void moveTo(Coord target)
    {
        if(self.canStep(target))
        {
            self.setLoc(target);
            self.dischargeMove();
        }
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
    
    // returns an intelligent path to a destination
    public Vector<Coord> getPathTo(Coord destination)
    {
        Coord selfLoc = self.getLoc();
        int top =    Math.min(selfLoc.y, destination.y) - AI_PATHING_RADIUS;
        int bottom = Math.max(selfLoc.y, destination.y) + AI_PATHING_RADIUS;
        int left =   Math.min(selfLoc.x, destination.x) - AI_PATHING_RADIUS;
        int right =  Math.max(selfLoc.x, destination.x) + AI_PATHING_RADIUS;
        int height = bottom - top;
        int width =  right - left;
        
        boolean[][] passArr = new boolean[width][height];
        for(int x = 0; x < width; x++)
        for(int y = 0; y < height; y++)
        {
            passArr[x][y] = self.canStep(x + left, y + top);
        }
        // assume origin and destination are passable
        passArr[selfLoc.x - left][selfLoc.y - top] = true;
        passArr[destination.x - left][destination.y - top] = true;
        
        Vector<Coord> path = FastStar.getPath(passArr, selfLoc.x - left, selfLoc.y - top, destination.x - left, destination.y - top);
        
        // translate to map coords
        for(Coord loc : path)
        {
            loc.x += left;
            loc.y += top;
        }
        return path;
    }
    
    public Coord getStepTowards(Coord target)
    {
        Direction dirTo = Direction.getDirectionTo(self.getLoc(), target);
        Coord step = self.getLoc();
        step.add(dirTo.getAsCoord());
        
        // keep looking if you can't step
        if(!self.canStep(step))
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
        }
        return step;
    }
}