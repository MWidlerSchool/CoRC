package Map;

import MyTools.Coord;
import java.util.Vector;

/*
A class for an A* implementation, using an ordered stack for the open list and a hash table for the closed list.

OL = open list, CL = closed list, H = estimated distance to target,
G = distance traveled so far
	
Procedure:
	1) Add origin node to OL
	2) Node P = the node which has the lowest H+G on the OL.
	3) For each passable cell adjacent to P that is not on the CL,
		if new cell == target, end.
		newG = P.G + entry cost of this node.
		If not on OL: Add to OL.  G = newG
		If on OL: if G > newG, G = newG, parent = P
		else do nothing
   4) Add P to CL.  Go to step 2.

Once you end, then follow the parents back for the target node to find the path.
Does not return the origin.
*/

public class FastStar
{
    private FastStarArray closedList;
    private FastStarStack openList;
    private boolean[][] passMap;
    private Coord start;
    private Coord end;
    private FastStarNode finalNode;
    
    public static final int MAX_LOOPS = 1000;
    public static final boolean CHECK_DIAGONAL = true;
    public static final int H_WEIGHT = 11;                  // tuning variable
    public static final int[][] ORTHO_LIST = {{1, 0, 10}, {-1, 0, 10}, {0, 1, 10}, {0, -1, 10}}; // x, y and distance for orthoginal steps
    public static final int[][] DIAG_LIST  = {{1, 0, 10}, {-1, 0, 10}, {0, 1, 10}, {0, -1, 10},  // x, y and distance for orthoginal and diagonal steps
                                              {1, 1, 14}, {1, -1, 14}, {-1, 1, 14}, {-1, -1, 14}};
    
    public static Vector<Coord> getPath(boolean[][] map, int startX, int startY, int endX, int endY)
    {
        FastStar fs = new FastStar(map);
        return fs.getPath(startX, startY, endX, endY);
    }
    
    public Vector<Coord> getPath(int startX, int startY, int endX, int endY)
    {
        start = new Coord(startX, startY);
        end = new Coord(endX, endY);
        
        openList.push(null, start, getH(start), 0);
        finalNode = mainLoop();
        return getPathList();
    }
    
    public FastStar(boolean[][] map)
    {
        passMap = map;
        closedList = new FastStarArray(passMap.length, passMap[0].length);
        openList = new FastStarStack();
        start = null;
        end = null;
        finalNode = null;
    }
    
    private int getH(Coord loc)
    {
        int x = loc.x - end.x;
        int y = loc.y - end.y;
        return (int)((Math.sqrt((x * x) + (y * y))) * H_WEIGHT);
    }
    
    private boolean isInArea(Coord c)
    {
        return c.x >= 0 && c.x < passMap.length &&
               c.y >= 0 && c.y < passMap[0].length;
    }
    
    // process the nodes, returning either the final node or null
    private FastStarNode mainLoop()
    {
        int loops = 0;
        boolean hasPath = false;
        FastStarNode finalNode = null;
        FastStarNode curNode;
        int[][] stepList = ORTHO_LIST;
        if(CHECK_DIAGONAL)
            stepList = DIAG_LIST;
        while(loops < MAX_LOOPS && openList.size() > 0 && hasPath == false)
        {
            curNode = openList.pop();
            for(int i = 0; i < stepList.length; i++)
            {
                Coord curLoc = new Coord(curNode.loc.x + stepList[i][0], curNode.loc.y + stepList[i][1]);
                if(isInArea(curLoc) && passMap[curLoc.x][curLoc.y] && !closedList.contains(curLoc))
                {
                    if(curLoc.equals(end))          // found end
                    {
                        finalNode = new FastStarNode(curNode, curLoc, getH(curLoc), stepList[i][2]);
                        openList.push(finalNode);
                        hasPath = true;
                    }
                    else                            // not at end
                    {
                        FastStarNode existingNode = openList.find(curLoc, curNode.h + 14);
                        if(existingNode == null)    // is not on open list
                        {
                            openList.push(curNode, curLoc, getH(curLoc), stepList[i][2]);
                        }
                        else                        // already on open list
                        {
                            existingNode.update(curNode, stepList[i][2]);
                        }
                    }
                }
            }
            closedList.add(curNode.loc);
            loops += 1;
        }
        return finalNode;
    }
    
    private Vector<Coord> getPathList()
    {
        Vector<Coord> startToEnd = new Vector<Coord>();
        Vector<Coord> endToStart = new Vector<Coord>();
        if(finalNode != null)
        {
            // trace the path back and extract Coords
            FastStarNode curNode = finalNode;
            while(curNode.loc.equals(start) == false)
            {
                endToStart.add(curNode.loc);
                curNode = curNode.parent;
            }
            // convert to proper order
            int endIndex = endToStart.size() - 1;
            for(int i = 0; i < endToStart.size(); i++)
            {
                startToEnd.add(endToStart.elementAt(endIndex - i));
            }
        }
        return startToEnd;
    }
    
    public void printToConsole()
    {
        int width = passMap.length;
        int height = passMap[0].length;
        char[][] printArr = new char[width][height];
        for(int x = 0; x < width; x++)
        for(int y = 0; y < height; y++)
        {
            if(passMap[x][y])
                printArr[x][y] = '.';
            else
                printArr[x][y] = '#';
        }
        printArr[start.x][start.y] = '@';
        printArr[end.x][end.y] = '!';
        
        if(finalNode != null)
        {
            Vector<Coord> pathList = getPathList();
            for(Coord loc : pathList)
            {
                printArr[loc.x][loc.y] = 'X';
            }
        }
        
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                System.out.print("" + printArr[x][y]);
            }
            System.out.println();
        }
    }
    
    
    // test method
    public static void main(String[] args)
    {
        int w = 30;
        int h = 10;
        Coord s = new Coord(0, h / 2);
        Coord e = new Coord(w - 1, h / 2);
        boolean[][] pMap = new boolean[w][h];
        char[][] charMap = new char[w][h];
        for(int x = 0; x < w; x++)
        for(int y = 0; y < h; y++)
        {
            pMap[x][y] = true;
        }
        for(int y = 1; y < h; y++)
        {
            pMap[5][y] = false;
            pMap[w - 6][h - y - 1] = false;
        }
        
        pMap[(w / 2)][(h / 2)] = false;
        pMap[(w / 2) - 1][(h / 2)] = false;
        pMap[(w / 2)][(h / 2) - 1] = false;
        pMap[(w / 2) - 1][(h / 2) - 1] = false;
        
        for(int x = 0; x < w; x++)
        for(int y = 0; y < h; y++)
        {
            if(pMap[x][y])
                charMap[x][y] = '.';
            else
                charMap[x][y] = '#';
        }
        charMap[s.x][s.y] = '@';
        charMap[e.x][e.y] = '?';
        
        Vector<Coord> walkPath = getPath(pMap, s.x, s.y, e.x, e.y);
        for(int i = 0; i < walkPath.size(); i++)
        {
            Coord loc = walkPath.elementAt(i);
            charMap[loc.x][loc.y] = 'X';
        }
        
        for(int y = 0; y < h; y++)
        {
            for(int x = 0; x < w; x++)
            {
                System.out.print("" + charMap[x][y]);
            }
            System.out.println();
        }
        
        // no walkPath
        System.out.println();
        pMap[w - 6][h - 1] = false;
        for(int x = 0; x < w; x++)
        for(int y = 0; y < h; y++)
        {
            if(pMap[x][y])
                charMap[x][y] = '.';
            else
                charMap[x][y] = '#';
        }
        charMap[s.x][s.y] = '@';
        charMap[e.x][e.y] = '?';
        
        walkPath = getPath(pMap, s.x, s.y, e.x, e.y);
        for(int i = 0; i < walkPath.size(); i++)
        {
            Coord loc = walkPath.elementAt(i);
            charMap[loc.x][loc.y] = 'X';
        }
        
        for(int y = 0; y < h; y++)
        {
            for(int x = 0; x < w; x++)
            {
                System.out.print("" + charMap[x][y]);
            }
            System.out.println();
        }
    }

}