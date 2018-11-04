package Engine;

/*
Objects which can be added to the initiative manager.
*/

public interface InitObj
{
    public static final int SLOW_SPEED = 8;
    public static final int NORMAL_SPEED = 4;
    public static final int FAST_SPEED = 2;
    public static final int FULLY_CHARGED = 10;
    
    public void charge();
    public boolean isCharged();
    public void discharge(int e);
    public void act();
    
    /*
    private int initCharge = InitObj.FULLY_CHARGED;
    public void charge(){initCharge += 1;}
    public boolean isCharged(){return initCharge >= FULLY_CHARGED;}
    public void discharge(int e){initCharge -= e;}
    public void act(){}
    */
}