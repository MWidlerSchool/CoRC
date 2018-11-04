package Map;

import MyTools.Coord;

/* Nodes for a fast(er than my last) A* pathing class */

public class FastStarNode
{
    public int f;
    public int g;
    public int h;
    public FastStarNode parent;
    public Coord loc;
    
    // Constructor
    public FastStarNode(FastStarNode parentNode, Coord location, int distToEnd, int stepDist)
    {
        loc = new Coord(location);
        parent = parentNode;
        g = 0;
        h = distToEnd;
        if(parent != null)
            g = parent.g + stepDist;
        f = g + h;
    }
    
    // If the prospective parent would make a shorter route, switch to it.
    public void update(FastStarNode prospectiveParent, int stepDist)
    {
        if(parent == null || prospectiveParent.g + stepDist < g)
        {
            parent = prospectiveParent;
            g = prospectiveParent.g + stepDist;
            f = g + h;
        }
    }
}
