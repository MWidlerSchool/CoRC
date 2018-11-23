package Engine;
import MyTools.*;

public class EngineTools
{
    public static boolean isAdjacent(Coord a, Coord b)
    {
        return MathTools.getAngbandMetric(a, b) == 1;
    }
}