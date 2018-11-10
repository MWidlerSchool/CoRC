package Engine;

import Ability.*;
import Actor.*;
import GUI.*;

public class CombatManager
{
    public static int getAttackDamage(Actor attacker, Attack attack)
    {
        int attackerStat = attacker.getStat(attack.getAttackerStat());
        return attack.getBaseDamage() * attacker.getStat(attack.getAttackerStat());
    }
    
    public static void resolveAttack(Actor attacker, Actor defender, Attack attack)
    {
        int damage = getAttackDamage(attacker, attack);
        if(attacker == GameObj.getPlayer() || defender == GameObj.getPlayer())
            MessagePanel.add(String.format("%s %s %s for %d damage!", attacker.getName(), attack.getVerb(), 
                             defender.getName(), damage));
        defender.applyDamage(damage);
    }
}   