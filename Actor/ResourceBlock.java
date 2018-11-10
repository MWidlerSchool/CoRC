package Actor;

public class ResourceBlock
{
	private int curHealth;
	private int maxHealth;
	private int curBlock;
	private int maxBlock;
	private int curStamina;
	private int maxStamina;


	public int getCurHealth(){return curHealth;}
	public int getMaxHealth(){return maxHealth;}
	public int getCurBlock(){return curBlock;}
	public int getMaxBlock(){return maxBlock;}
	public int getCurStamina(){return curStamina;}
	public int getMaxStamina(){return maxStamina;}


	public void setCurHealth(int c){curHealth = c;}
	public void setMaxHealth(int m){maxHealth = m;}
	public void setCurBlock(int c){curBlock = c;}
	public void setMaxBlock(int m){maxBlock = m;}
	public void setCurStamina(int c){curStamina = c;}
	public void setMaxStamina(int m){maxStamina = m;}

    
    public ResourceBlock(boolean isPlayer)
    {
        if(isPlayer)
        {
            maxHealth = ActorConstants.DEFAULT_PLAYER_HEALTH;
            maxBlock = ActorConstants.DEFAULT_PLAYER_BLOCK;
            maxStamina = ActorConstants.DEFAULT_PLAYER_BLOCK;
        }
        else
        {
            maxHealth = ActorConstants.DEFAULT_ENEMY_HEALTH;
            maxBlock = 0;
            maxStamina = 0;
        }
        fullHeal();
    }
    
    public ResourceBlock()
    {
        this(false);
    }
    
    public void fullHeal()
    {
        curHealth = getMaxHealth();
        curBlock = getMaxBlock();
        curStamina = getMaxStamina();
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