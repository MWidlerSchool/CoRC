package Map;

import MyTools.Coord;

/* An ordered stack, implemented with a double linked list. */

public class FastStarStack
{
    private StackObj head = null;
    private int size = 0;
    
    public int size(){return size;}
    
    public FastStarNode pop()
    {
        StackObj returnVal = head;
        returnVal.remove();
        size -= 1;
        if(size == 0)
            head = null;
        else
            head = returnVal.next;
        return returnVal.node;
    }
    
    public void push(FastStarNode newNode)
    {
        StackObj newObj = new StackObj(newNode);
        if(head == null)
            head = newObj;
        else
        {
            StackObj curObj = head;
            if(newObj.node.h < curObj.node.h)
            {
                newObj.insertBefore(curObj);
                head = newObj;
            }
            else
            {
                curObj = curObj.next;
                boolean isInserted = false;
                while(curObj != head)
                {
                    if(newObj.node.h < curObj.node.h)
                    {
                        newObj.insertBefore(curObj);
                        isInserted = true;
                        break;
                    }
                    curObj = curObj.next;
                }
                // add to end of list
                if(!isInserted)
                {
                    newObj.insertBefore(head);
                }
            }
        }
    }
    
    private class StackObj
    {
        public StackObj next = null;
        public StackObj previous = null;
        public FastStarNode node;
        
        public StackObj(FastStarNode n)
        {
            node = n;
            next = this;
            previous = this;
        }
        
        public void insertBefore(StackObj that)
        {
            this.next = that;
            this.previous = that.previous;
            this.previous.next = this;
            this.next.previous = this;
        }
        
        public void remove()
        {
            next.previous = previous;
            previous.next = next;
        }
    }
    
    
    

    
    public static void main(String[] args)
    {
        //FastStarNode(FastStarNode parentNode, Coord location, int distToEnd, int stepDist)
        FastStarNode[] nodeArr = new FastStarNode[5];
        for(int i = 0; i < 5; i++)
        {
            nodeArr[i] = new FastStarNode(null, new Coord(), 0, 0);
        }
        nodeArr[0].h = 45;
        nodeArr[1].h = 15;
        nodeArr[2].h = 73;
        nodeArr[3].h = 4;
        nodeArr[4].h = 92;
        
        FastStarStack stack = new FastStarStack();
        for(int i = 0; i < 5; i++)
            stack.push(nodeArr[i]);
        for(int i = 0; i < 5; i++)
            System.out.println("" + stack.pop().h);
    }
}