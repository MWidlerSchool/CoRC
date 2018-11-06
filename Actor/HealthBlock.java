package Actor;

public class HealthBlock
{
	private int curHealth;
	private int maxHealth;
	private int curBlock;
	private int maxBlock;


	public int getCurHealth(){return curHealth;}
	public int getMaxHealth(){return maxHealth;}
	public int getCurBlock(){return curBlock;}
	public int getMaxBlock(){return maxBlock;}


	public void setCurHealth(int c){curHealth = c;}
	public void setMaxHealth(int m){maxHealth = m;}
	public void setCurBlock(int c){curBlock = c;}
	public void setMaxBlock(int m){maxBlock = m;}
    
    public HealthBlock(boolean isPlayer)
    {
        if(isPlayer)
        {
            maxHealth = ActorConstants.DEFAULT_PLAYER_HEALTH;
            maxBlock = ActorConstants.DEFAULT_PLAYER_BLOCK;
        }
        else
        {
            maxHealth = ActorConstants.DEFAULT_ENEMY_HEALTH;
            maxBlock = 0;
        }
        fullHeal();
    }
    
    public HealthBlock()
    {
        this(false);
    }
    
    public void fullHeal()
    {
        curHealth = getMaxHealth();
        curBlock = getMaxBlock();
    }
    
    public boolean isDead()
    {
        if(curHealth < 1)
            return true;
        return false;
    }
    
    public void applyDamage(int dmg)
    {
        curBlock -= dmg;
        if(curBlock < 0)
        {
            curHealth += curBlock;
            curBlock = 0;
        }
        if(curHealth < 0)
            curHealth = 0;
    }
}