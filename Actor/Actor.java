package Actor;

import MyTools.*;
import GUI.*;
import Map.*;
import AI.*;
import Engine.*;

public class Actor extends ScreenObj implements InitObj
{
    private AIBase ai;
    private int initCharge = InitObj.FULLY_CHARGED;
    private StatBlock statBlock;
    
    public AIBase getAI(){return ai;}
    public StatBlock getStatBlock(){return statBlock;}
    
    public void setAI(AIBase newAI){ai = newAI;}
    public void setStatBlock(StatBlock s){statBlock = s;}
    
    public Actor()
    {
        this('?');
    }
    
    public Actor(char c)
    {
        super(TileSet.getMapTile(c));
        ai = new AIBase(this);
        statBlock = new StatBlock();
    }
    
    public boolean canStep(Coord c){return canStep(GameObj.getMap().getCell(c));}
    public boolean canStep(MapCell cell)
    {
        return cell.isLowPassable();
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
        return a;
    }
}