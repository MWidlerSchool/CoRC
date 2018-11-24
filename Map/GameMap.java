package Map;

import MyTools.*;
import Item.*;

public class GameMap
{
    private MapCell map[][];
    private int height;
    private int width;
    private MapCell oobTile = MapCell.NULL;
    
    public MapCell getCell(Coord c){return getCell(c.x, c.y);}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    
    
    public GameMap(int w, int h, char defaultTerrain)
    {
        width = w;
        height = h;
        map = new MapCell[width][height];
        for(int x = 0; x < width; x++)
        for(int y = 0; y < height; y++)
            map[x][y] = new MapCell(defaultTerrain);
    }
    
    public boolean isInBounds(Coord c){return isInBounds(c.x, c.y);}
    public boolean isInBounds(int x, int y)
    {
        if(x < width && y < height && x >= 0 && y >= 0)
            return true;
        return false;
    }
    
    
    public MapCell getCell(int x, int y)
    {
        if(x < 0 || y < 0 || x >= width || y >= height)
            return MapCell.NULL;
        return map[x][y];
    }
    
    
    public Item getItem(Coord c){return getItem(c.x, c.y);}
    public Item getItem(int x, int y)
    {
        if(isInBounds(x, y))
            map[x][y].getItem();
        return null;
    }
    
    
    public Item takeItem(Coord c){return takeItem(c.x, c.y);}
    public Item takeItem(int x, int y)
    {
        if(isInBounds(x, y))
            map[x][y].takeItem();
        return null;
    }
    
    // this will overwrite any existing item
    public void setItem(Item i, Coord c){setItem(i, c.x, c.y);}
    public void setItem(Item i, int x, int y)
    {
        if(isInBounds(x, y))
            map[x][y].setItem(i);
    }
    
    // find nearest place for this
    public void dropItem(Item item, Coord c){dropItem(item, c.x, c.y);}
    public void dropItem(Item item, int xLoc, int yLoc)
    {
        if(getCell(xLoc, yLoc).canPlaceItem())
        {
            setItem(item, xLoc, yLoc);
        }
        else
        {
            int xOrigin = xLoc - 5;
            int yOrigin = yLoc - 5;
            boolean[][] placeMap = new boolean[11][11];
            for(int x = 0; x < 11; x++)
            for(int y = 0; y < 11; y++)
            {
                placeMap[x][y] = getCell(x + xOrigin, y + yOrigin).isLowPassable();
            }
            SpiralSearch search = new SpiralSearch(placeMap, new Coord(5, 5), true);
            Coord c = null;
            boolean hasTarget = false;
            for(int i = 0; i < 36; i++)
            {
                c = search.getNext();
                if(getCell(c.x + xOrigin, c.y + yOrigin).canPlaceItem())
                {
                    hasTarget = true;
                    break;
                }
            }
            if(hasTarget)
                getCell(c.x + xOrigin, c.y + yOrigin).setItem(item);
        }
    }
    
    public static GameMap getTestMap()
    {
        GameMap m = new GameMap(49, 49, '.');
        for(int x = 0; x < 49; x++)
        {
            m.map[x][0] = new MapCell('#');
            m.map[x][48] = new MapCell('#');
        }
        for(int y = 0; y < 49; y++)
        {
            m.map[0][y] = new MapCell('#');
            m.map[48][y] = new MapCell('#');
        }
        
        m.map[3][3] = new MapCell('#');
        m.map[3][4] = new MapCell('#');
        m.map[4][3] = new MapCell('#');
        m.map[4][4] = new MapCell('#');
        
        m.map[1][2] = new MapCell('=');
        m.map[2][2] = new MapCell('=');
        m.map[3][2] = new MapCell('=');
        m.map[4][2] = new MapCell('=');
        m.map[4][1] = new Door();
        
        for(int x = 5; x < 44; x++)
        for(int y = 5; y < 44; y++)
        {
            if(Math.random() < .25)
                m.map[x][y] = new MapCell('#');
        }
        
        for(int x = 5; x < 10; x++)
        for(int y = 1; y < 5; y++)
            m.map[x][y] = new MapCell('.');
        
        m.map[5][5] = new MapCell('#');
        m.map[6][5] = new MapCell('#');
        m.map[7][5] = new MapCell('#');
        m.map[8][5] = new MapCell('#');
        m.map[8][4] = new MapCell('#');
        m.map[8][3] = new MapCell('#');
        m.map[8][2] = new MapCell('#');
        
        m.dropItem(new Item(']'), 2, 1);
        m.dropItem(new Item('}'), 2, 1);
        m.dropItem(new Item(')'), 2, 1);
        m.dropItem(new Item('"'), 2, 1);
        return m;
    }
}  