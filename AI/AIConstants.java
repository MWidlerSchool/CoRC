package AI;

public interface AIConstants
{
    public static int AI_PATHING_RADIUS = 15;   // determine margin of array passed to FastStar
    public static int DEFAULT_MEMORY_LENGTH = 7;       // in turns
    
    
    // teams are friendly, hostile, or neutral towards each other.
    public enum Team
    {
        HERO, VILLAIN, POLICE, CIVILIAN, VIGILANTE, ANIMAL, BERSERK, PASSIVE;
        
        public boolean isFriendly(Team that)
        {
            boolean friendly = false;
            if(this == that)    // everyone is their own friendly
                friendly = true;
            if(this == HERO)
            {
                if(that == POLICE || that == CIVILIAN)
                    friendly = true;
            }
            else if(this == POLICE)
            {
                if(that == CIVILIAN || that == HERO)
                    friendly = true;
            }
            else if(this == CIVILIAN)
            {
                if(that == POLICE || that == HERO)
                    friendly = true;
            }
            return friendly;
        }
        
        public boolean isHostile(Team that)
        {
            boolean hostile = false;
            if(this == HERO || this == VIGILANTE)
            {
                if(that == VILLAIN)
                    hostile = true;
            }
            else if(this == VILLAIN)
            {
                if(that == HERO || that == POLICE || that == CIVILIAN || that == VIGILANTE)
                    hostile = true;
            }
            else if(this == POLICE)
            {
                if(that == VILLAIN || that == VIGILANTE)
                    hostile = true;
            }
            else if(this == BERSERK)
                    hostile = true;
            else if(this == PASSIVE || this == ANIMAL)
            {
                // regular animals are indifferent; trained animals are on a team
            }
            
            return hostile;
        }
    }
}