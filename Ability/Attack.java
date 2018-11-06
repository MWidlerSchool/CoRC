package Ability;

import Actor.*;

/*
The class for attacks. Attacks are xd10, against a TN of y, dealing successes * baseDamage.
*/

public class Attack
{
	private String name;
	private String verb;
	private int attackerStat;
	private int defenderStat;
	private int baseDamage;


	public String getName(){return name;}
	public String getVerb(){return verb;}
	public int getAttackerStat(){return attackerStat;}
	public int getDefenderStat(){return defenderStat;}
	public int getBaseDamage(){return baseDamage;}


	public void setName(String n){name = n;}
	public void setVerb(String v){verb = v;}
	public void setAttackerStat(int a){attackerStat = a;}
	public void setDefenderStat(int d){defenderStat = d;}
	public void setBaseDamage(int b){baseDamage = b;}


    public Attack(String n, String v, int aStat, int dStat, int bDmg)
    {
        name = n;
        verb = v;
        attackerStat = aStat;
        defenderStat = dStat;
        baseDamage = bDmg;
    }
    
    public Attack()
    {
        this("Strike", "strikes", ActorConstants.AGILITY, ActorConstants.AGILITY, 5);
    }
}