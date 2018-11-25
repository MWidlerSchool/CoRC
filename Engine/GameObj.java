package Engine;

import MyTools.*;
import Actor.*;
import Map.*;
import java.util.*;
import GUI.*;


public class GameObj
{
	private static Actor player;
	private static GameMap map;
    private static Vector<Actor> actorList = new Vector<Actor>();
    private static InitManager initManager;
    private static boolean masterHold = true;
    private static CoRCTimer timer;
    


	public static Actor getPlayer(){return player;}
	public static GameMap getMap(){return map;}
    public static Vector<Actor> getActorList(){return actorList;}
    public static boolean masterHold(){return masterHold;}


	public static void setPlayer(Actor p){player = p;}
	public static void setMap(GameMap m){map = m;}
    public static void hold(){masterHold = true;}
    public static void resume(){masterHold = false;}

    
    public static void init(CoRCTimer t)
    {
        map = GameMap.getTestMap();
        initManager = new InitManager();
        ActorLocManager.init(map.getWidth(), map.getHeight());
        timer = t;
        
        player = Actor.getTestPlayer();
        player.setLoc(1, 1);
        Actor testEnemy = Actor.getTestEnemy();
        testEnemy.setLoc(3, 6);
        add(player);
        add(testEnemy);
        testEnemy = Actor.getTestEnemy();
        testEnemy.setLoc(4, 6);
        add(testEnemy);
        testEnemy = Actor.getTestEnemy();
        testEnemy.setLoc(5, 6);
        add(testEnemy);
    }
    
    public static void add(Actor a)
    {
        actorList.add(a);
        initManager.add(a);
        ActorLocManager.add(a);
    }
    
    public static void remove(Actor a)
    {
        actorList.remove(a);
        initManager.remove(a);
        ActorLocManager.remove(a);
    }
    
    public static boolean isActorAt(Coord c){return isActorAt(c.x, c.y);}
    public static boolean isActorAt(int x, int y){return getActorAt(x, y) != null;}
    public static Actor getActorAt(Coord c){return getActorAt(c.x, c.y);}
    public static Actor getActorAt(int x, int y)
    {
        return ActorLocManager.getActorAt(x, y);
    }
    
    public static void pauseTurns(int ticks)
    {
        if(ticks > 1)
        {
            initManager.setTurnPause(true);
            if(timer.getTurnDelay() < ticks)
                timer.setTurnDelay(ticks);
        }
    }
}