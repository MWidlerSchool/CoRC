package Engine;

import Ability.*;
import Actor.*;
import GUI.*;

public class CombatManager
{
    public static int getAttackDamage(Actor attacker, Actor defender, Attack attack)
    {
        int attackerStat = attacker.getStat(attack.getAttackerStat());
        int defenderStat = defender.getStat(attack.getDefenderStat());
        int damage = EngineTools.skillCheck(attackerStat, defenderStat);
        return damage * (attack.getBaseDamage() + attacker.getStat(attack.getAttackerStat()));
    }
    
    public static void resolveAttack(Actor attacker, Actor defender, Attack attack)
    {
        int damage = getAttackDamage(attacker, defender, attack);
        if(attacker == GameObj.getPlayer() || defender == GameObj.getPlayer())
            MessagePanel.add(String.format("%s %s %s for %d damage!", attacker.getName(), attack.getVerb(), 
                             defender.getName(), damage));
        defender.applyDamage(damage);
    }
}   