package Map;

import MyTools.Coord;

/*
A class for an A* closed list. A 2D array is used to get a O(1) for insertion and indexing.
*/

public class FastStarArray
{
    private boolean[][] arr;    // the internal arry
    
    // constructor
    public FastStarArray(int width, int height)
    {
        arr = new boolean[width][height];
    }
    
    // mark a location as closed
    public void add(Coord c)
    {
        arr[c.x][c.y] = true;
    }
    
    // check if a location is closed
    public boolean contains(Coord c)
    {
        return arr[c.x][c.y];
    }
}