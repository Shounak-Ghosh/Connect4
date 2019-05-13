package Connect4;

import java.awt.Color;

/**
 * 
 */
public abstract class Player
{
    protected Color color;
    protected String name;
    protected GameDisplay display;
    
    public Color getColor() {
        return color;
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() 
    {
        return name;
    }
    
    public abstract int getMove();
}
