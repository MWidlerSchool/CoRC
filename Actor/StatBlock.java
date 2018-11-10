package Actor;

import Engine.*;

public class StatBlock
{
	private int moveSpeed;
	private int actionSpeed;
	private int visionRadius;
	private int body;
	private int mind;
	private int spirit;


	public int getMoveSpeed(){return moveSpeed;}
	public int getActionSpeed(){return actionSpeed;}
	public int getVisionRadius(){return visionRadius;}
	public int getBody(){return body;}
	public int getMind(){return mind;}
	public int getSpirit(){return spirit;}


	public void setMoveSpeed(int m){moveSpeed = m;}
	public void setActionSpeed(int a){actionSpeed = a;}
	public void setVisionRadius(int v){visionRadius = v;}
	public void setBody(int b){body = b;}
	public void setMind(int m){mind = m;}
	public void setSpirit(int s){spirit = s;}


    
    
    public StatBlock()
    {
        moveSpeed = InitObj.NORMAL_SPEED;
        actionSpeed = InitObj.NORMAL_SPEED;
        visionRadius = 12;
        body = 4;
        mind = 4;
        spirit = 4;
    }
}