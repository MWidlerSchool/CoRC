package Actor;

import Engine.*;

public class StatBlock
{
	private int moveSpeed;
	private int actionSpeed;
    private int visionRadius;


	public int getMoveSpeed(){return moveSpeed;}
	public int getActionSpeed(){return actionSpeed;}
    public int getVisionRadius(){return visionRadius;}


	public void setMoveSpeed(int m){moveSpeed = m;}
	public void setActionSpeed(int a){actionSpeed = a;}
    public void setVisionRadius(int v){visionRadius = v;}
    
    
    public StatBlock()
    {
        moveSpeed = InitObj.NORMAL_SPEED;
        actionSpeed = InitObj.NORMAL_SPEED;
        visionRadius = 12;
    }
}