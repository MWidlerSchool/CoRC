package Engine;


public class RNG
{
    public static int nextInt(int max)
    {
        return (int)(Math.random() * max);
    }
    
    public static int roll(int sides)
    {
        return nextInt(sides) + 1;
    }
    
    public static double nextDouble()
    {
        return Math.random();
    }
    
    public static boolean nextBoolean()
    {
        return Math.random() >= 0.5;
    }
}