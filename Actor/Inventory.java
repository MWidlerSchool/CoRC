package Actor;

import Item.*;
import java.util.*;

public class Inventory
{
	private Vector<Item> list;
	private Actor owner;


	public Vector<Item> getList(){return list;}
	public Actor getOwner(){return owner;}


	public void setList(Vector<Item> l){list = l;}
	public void setOwner(Actor o){owner = o;}


    public Inventory(Actor o)
    {
        owner = o;
        list = new Vector<Item>();
    }
    
    public int size()
    {
        return list.size();
    }
    
    public boolean isFull()
    {
        return size() >= ActorConstants.MAX_INVENTORY_SIZE;
    }
    
    public boolean add(Item item)
    {
        if(isFull())
            return false;
        list.add(item);
        return true;
    }
    
    public Item getItem(int index)
    {
        return list.elementAt(index);
    }
    
    public Item takeItem(int index)
    {
        Item item = list.elementAt(index);
        list.removeElementAt(index);
        return item;
    }
}