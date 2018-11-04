package Engine;

import MyTools.*;
import Actor.*;
import Map.*;
import java.util.*;


public class GameObj
{
	private static Actor player;
	private static GameMap map;
    private static Vector<Actor> actorList = new Vector<Actor>();
    public static InitManager initManager;


	public static Actor getPlayer(){return player;}
	public static GameMap getMap(){return map;}
    public static Vector<Actor> getActorList(){return actorList;}


	public static void setPlayer(Actor p){player = p;}
	public static void setMap(GameMap m){map = m;}

    
    public static void init()
    {
        player = new Actor('@');
        player.setLoc(1, 1);
        map = GameMap.getTestMap();
        initManager = new InitManager();
        
        Actor testEnemy = Actor.getTestEnemy();
        testEnemy.setLoc(3, 6);
        
        actorList = new Vector<Actor>();
        actorList.add(player);
        actorList.add(testEnemy);
        initManager.add(player);
        
        initManager.add(testEnemy);
        for(int i = 0; i < actorList.size(); i++)
            initManager.add(actorList.elementAt(i));
    }
    
}