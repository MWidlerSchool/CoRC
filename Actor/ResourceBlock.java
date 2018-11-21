package Actor;

import GUI.*;

public class ResourceBlock
{
	private int curHealth;
	private int maxHealth;
	private int curBlock;
	private int maxBlock;
	private int curStamina;
	private int maxStamina;
    private int blockRecoveryTime;
    private int turnsSinceHit;
    private Actor self;


	public int getCurHealth(){return curHealth;}
	public int getMaxHealth(){return maxHealth;}
	public int getCurBlock(){return curBlock;}
	public int getMaxBlock(){return maxBlock;}
	public int getCurStamina(){return curStamina;}
	public int getMaxStamina(){return maxStamina;}
    public int getBlockRecoveryTime(){return blockRecoveryTime;}


	public void setCurHealth(int c){curHealth = c;}
	public void setMaxHealth(int m){maxHealth = m;}
	public void setCurBlock(int c){curBlock = c;}
	public void setMaxBlock(int m){maxBlock = m;}
	public void setCurStamina(int c){curStamina = c;}
	public void setMaxStamina(int m){maxStamina = m;}
    public void setBlockRecoveryTime(int b){blockRecoveryTime = b;}

    
    public ResourceBlock(Actor actor, boolean isPlayer)
    {
        self = actor;
        if(isPlayer)
        {
            maxHealth = ActorConstants.DEFAULT_PLAYER_HEALTH;
            maxBlock = ActorConstants.DEFAULT_PLAYER_BLOCK;
            maxStamina = ActorConstants.DEFAULT_PLAYER_BLOCK;
            blockRecoveryTime = ActorConstants.DEFAULT_BLOCK_RECOVERY_TIME;
        }
        else
        {
            maxHealth = ActorConstants.DEFAULT_ENEMY_HEALTH;
            maxBlock = 0;
            maxStamina = 0;
        }
        fullHeal();
    }
    
    public ResourceBlock(Actor actor)
    {
        this(actor, false);
    }
    
    public void fullHeal()
    {
        curHealth = getMaxHealth();
        curBlock = getMaxBlock();
        curStamina = getMaxStamina();
        turnsSinceHit = blockRecoveryTime;
    }
    
    public boolean isDead()
    {
        if(curHealth < 1)
            return true;
        return false;
    }
    
    public void applyDamage(int dmg)
    {
        boolean startedWithBlock = curBlock > 0;
        curBlock -= dmg;
        if(curBlock < 0)
        {
            curHealth += curBlock;
            curBlock = 0;
            if(startedWithBlock)
            {
                VisualEffectsManager.add(VisualEffectsFactory.getFloatMessage("Block Broken!", GUIConstants.BLOCK_COLOR, self.getLoc()));
            }
        }
        if(curHealth < 0)
            curHealth = 0;
        
        turnsSinceHit = 0;
    }
    
    public void endTurn()
    {
        if(turnsSinceHit < blockRecoveryTime)
        {
            turnsSinceHit += 1;
            if(turnsSinceHit >= blockRecoveryTime && getCurBlock() < getMaxBlock())
            {
                curBlock = getMaxBlock();
                VisualEffectsManager.add(VisualEffectsFactory.getFloatMessage("Block Recovered!", GUIConstants.BLOCK_COLOR, self.getLoc()));
            }
        }
    }
}