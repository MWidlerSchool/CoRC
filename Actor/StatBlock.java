package Actor;

import Engine.*;

public class StatBlock
{
	private int moveSpeed;
	private int actionSpeed;
	private int visionRadius;
	private int agility;
	private int strength;
	private int intellect;
	private int will;


	public int getMoveSpeed(){return moveSpeed;}
	public int getActionSpeed(){return actionSpeed;}
	public int getVisionRadius(){return visionRadius;}
	public int getAgility(){return agility;}
	public int getStrength(){return strength;}
	public int getIntellect(){return intellect;}
	public int getWill(){return will;}


	public void setMoveSpeed(int m){moveSpeed = m;}
	public void setActionSpeed(int a){actionSpeed = a;}
	public void setVisionRadius(int v){visionRadius = v;}
	public void setAgility(int a){agility = a;}
	public void setStrength(int s){strength = s;}
	public void setIntellect(int i){intellect = i;}
	public void setWill(int w){will = w;}

    
    
    public StatBlock()
    {
        moveSpeed = InitObj.NORMAL_SPEED;
        actionSpeed = InitObj.NORMAL_SPEED;
        visionRadius = 12;
        agility = 3;
        strength = 3;
        intellect = 3;
        will = 3;
    }
}