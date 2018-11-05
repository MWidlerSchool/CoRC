package Actor;

import MyTools.*;
import GUI.*;
import Map.*;
import AI.*;
import Engine.*;
import java.util.*;

public class Actor extends ScreenObj implements InitObj
{
    private AIBase ai;
    private int initCharge = InitObj.FULLY_CHARGED;
    private StatBlock statBlock;
    private Coord loc;
    private String name;
    
    public AIBase getAI(){return ai;}
    public StatBlock getStatBlock(){return statBlock;}
    public Coord getLoc(){return new Coord(loc);}
    public String getName(){return name;}
    
    public void setAI(AIBase newAI){ai = newAI;}
    public void setStatBlock(StatBlock s){statBlock = s;}
    public void setLoc(Coord c){setLoc(c.x, c.y);}
    public void setName(String n){name = n;}
    
    // primary construtor
    public Actor(char c)
    {
        super(TileSet.getMapTile(c));
        ai = new AIBase(this);
        statBlock = new StatBlock();
        loc = new Coord();
        name = "Unknown Actor";
    }
    
    public Actor()
    {
        this('?');
    }
    
    // all location setting must pass through this, or the loc manager doesn't get updated.
    public void setLoc(int x, int y)
    {
        // make sure objects are initialized and valid
        if(GameObj.getMap() != null &&              // map object exists
           GameObj.getMap().isInBounds(x, y) &&     // new location is valid
           GameObj.getMap().isInBounds(getLoc()))   // not setting initial location
        {
                ActorLocManager.update(this, getLoc(), new Coord(x, y));
        }
        loc.x = x; 
        loc.y = y;
     }
    
    public boolean canStep(Coord c){return canStep(c.x, c.y);}
    public boolean canStep(int x, int y)
    {
        MapCell cell = GameObj.getMap().getCell(x, y);
        return cell.isLowPassable() && (!GameObj.isActorAt(x, y));
    }
    
    
    public boolean canSee(Actor that){return canSee(that.getLoc());}
    public boolean canSee(Coord target)
    {
        // check vision radius
        if(MathTools.getAngbandMetric(getLoc(), target) > getStatBlock().getVisionRadius())
            return false;
        
        // check for obstructions
        Vector<Coord> line = StraightLine2.findLine(getLoc(), target, StraightLine2.REMOVE_ORIGIN_AND_TARGET);
        for(int i = 0; i < line.size(); i++)
        {
            if(GameObj.getMap().getCell(line.elementAt(i)).isTransparent() == false)
                return false;
        }
        
        return true;
    }
    
    // InitObj functions    
    public void charge(){initCharge += 1;}
    public boolean isCharged(){return initCharge >= FULLY_CHARGED;}
    public void discharge(int e){initCharge -= e;}
    
    public void act()
    {
        ai.plan();
        if(ai.hasPlan())
            ai.act();
    }
    
    public void dischargeMove()
    {
        discharge(getStatBlock().getMoveSpeed());
    }
    
    public void dischargeAction()
    {
        discharge(getStatBlock().getActionSpeed());
    }
    
    /////////////////////////////////////////////////////////
    
    public static Actor getTestEnemy()
    {
        Actor a = new Actor('e');
        a.setName("Test Enemy");
        a.setAI(new ZombieAI(a));
        return a;
    }
}