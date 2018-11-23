package Actor;

import MyTools.*;
import GUI.*;
import Map.*;
import AI.*;
import Engine.*;
import Ability.*;
import Item.*;
import java.util.*;

public class Actor extends ScreenObj implements InitObj
{
    private AIBase ai;
    private int initCharge = InitObj.FULLY_CHARGED;
    private StatBlock statBlock;
    private ResourceBlock resourceBlock;
    private Coord loc;
    private String name;
    private Attack basicAttack;
    private Inventory inventory;
    private boolean takingTurn;
    
    public AIBase getAI(){return ai;}
    public StatBlock getStatBlock(){return statBlock;}
    public ResourceBlock getResourceBlock(){return resourceBlock;}
    public Coord getLoc(){return new Coord(loc);}
    public String getName(){return name;}
    public Attack getBasicAttack(){return basicAttack;}
    public Inventory getInventory(){return inventory;}
    
    public void setAI(AIBase newAI){ai = newAI;}
    public void setStatBlock(StatBlock s){statBlock = s;}
    public void setResourceBlock(ResourceBlock h){resourceBlock = h;}
    public void setLoc(Coord c){setLoc(c.x, c.y);}
    public void setName(String n){name = n;}
    public void setBasicAttack(Attack a){basicAttack = a;}
    
    // primary construtor
    public Actor(char c)
    {
        super(c);
        ai = new AIBase(this);
        statBlock = new StatBlock();
        resourceBlock = new ResourceBlock(this);
        loc = new Coord();
        name = "Unknown Actor";
        basicAttack = new Attack();
        inventory = new Inventory(this);
        takingTurn = false;
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
        if(MathTools.getDistanceMetric(getLoc(), target) > getStatBlock().getVisionRadius() * getStatBlock().getVisionRadius())
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
        if(!takingTurn)
        {
            takingTurn = true;
            beginTurn();
        }
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
    
    public void beginTurn()
    {
        ai.beginTurn();
    }
    
    public void endTurn()
    {
        resourceBlock.endTurn();
        takingTurn = false;
    }
    
    // AI functions
    ////////////////////////////////////////////////////
    
    public boolean isHostile(Actor that)
    {
        return getAI().isHostile(that);
    }
    
    public boolean isFriendly(Actor that)
    {
        return getAI().isFriendly(that);
    }
    
    public void alert(Actor enemy)
    {
        getAI().getMemory().updateEntry(enemy);
    }
    
    // stat functions
    ////////////////////////////////////////////////////
    
    public int getStat(int statEnum)
    {
        switch(statEnum)
        {
            case ActorConstants.BODY        : return getStatBlock().getBody();
            case ActorConstants.MIND        : return getStatBlock().getMind();
            case ActorConstants.SPIRIT      : return getStatBlock().getSpirit();
            default                         : return 0;
        }
    }
    
    public void applyDamage(int dmg)
    {
        getResourceBlock().applyDamage(dmg);
        if(getResourceBlock().isDead())
        {
            die();
        }
    }
    
    public void die()
    {
        GameObj.remove(this);
    }
    
    /////////////////////////////////////////////////////////
    
    public static Actor getTestEnemy()
    {
        Actor a = new Actor('e');
        a.setName("Test Enemy");
        a.setAI(new WolfAI(a));
        return a;
    }
    
    public static Actor getTestPlayer()
    {
        Actor a = new Actor('@');
        a.setName("Test Player");
        a.setAI(new PlayerAI(a));
        a.setResourceBlock(new ResourceBlock(a, true));
        return a;
    }
}