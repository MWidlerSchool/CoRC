package Map;

import MyTools.*;

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
        
        for(int x = 5; x < 44; x++)
        for(int y = 5; y < 44; y++)
        {
            if(Math.random() < .25)
                m.map[x][y] = new MapCell('#');
        }
        return m;
    }
}  