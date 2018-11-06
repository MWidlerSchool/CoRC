package Engine;

public class EngineTools
{
    // roll diced10, with TN difficulty. return number of successes
    public static int skillCheck(int dice, int difficulty)
    {
        dice = Math.min(10, dice);
        dice = Math.max(1, dice);
        difficulty = Math.min(10, difficulty);
        difficulty = Math.max(2, difficulty);
        int result = 0;
        for(int i = 0; i < dice; i++)
            if(RNG.roll(10) >= difficulty)
                result += 1;
        return result;
    }
}