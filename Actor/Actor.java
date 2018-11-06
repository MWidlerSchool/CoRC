package Actor;

import MyTools.*;
import GUI.*;
import Map.*;
import AI.*;
import Engine.*;
import Ability.*;
import java.util.*;

public class Actor extends ScreenObj implements InitObj
{
    private AIBase ai;
    private int initCharge = InitObj.FULLY_CHARGED;
    private StatBlock statBlock;
    private HealthBlock healthBlock;
    private Coord loc;
    private String name;
    private Attack basicAttack;
    
    public AIBase getAI(){return ai;}
    public StatBlock getStatBlock(){return statBlock;}
    public HealthBlock getHealthBlock(){return healthBlock;}
    public Coord getLoc(){return new Coord(loc);}
    public String getName(){return name;}
    public Attack getBasicAttack(){return basicAttack;}
    
    public void setAI(AIBase newAI){ai = newAI;}
    public void setStatBlock(StatBlock s){statBlock = s;}
    public void setHealthBlock(HealthBlock h){healthBlock = h;}
    public void setLoc(Coord c){setLoc(c.x, c.y);}
    public void setName(String n){name = n;}
    public void setBasicAttack(Attack a){basicAttack = a;}
    
    // primary construtor
    public Actor(char c)
    {
        super(TileSet.getMapTile(c));
        ai = new AIBase(this);
        statBlock = new StatBlock();
        healthBlock = new HealthBlock();
        loc = new Coord();
        name = "Unknown Actor";
        basicAttack = new Attack();
    }
    
    public Actor()
    {
        this('?');
    }
    
    
    // vision and movement
    /////////////////////////////////////////////////////////
    
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
    
    // stat functions
    ////////////////////////////////////////////////////
    
    public int getStat(int statEnum)
    {
        switch(statEnum)
        {
            case ActorConstants.AGILITY     : return getStatBlock().getAgility();
            case ActorConstants.STRENGTH    : return getStatBlock().getStrength();
            case ActorConstants.INTELLECT   : return getStatBlock().getIntellect();
            case ActorConstants.WILL        : return getStatBlock().getWill();
            default                         : return 0;
        }
    }
    
    public void applyDamage(int dmg)
    {
        getHealthBlock().applyDamage(dmg);
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