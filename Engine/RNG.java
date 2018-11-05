package Engine;


public class RNG
{
    public static int randomInt(int max)
    {
        return (int)(Math.random() * max);
    }
    
    public static double randomDouble()
    {
        return Math.random();
    }
    
    public static boolean randomBoolean()
    {
        return Math.random() >= 0.5;
    }
}