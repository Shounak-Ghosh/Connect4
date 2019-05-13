package Connect4;

import java.awt.Color;

/**
 * 
 */
public abstract class Player
{
    protected Color color;
    protected String name;
    
    public Color getColor() {
        return color;
    }
    
    public String toString() 
    {
        return name;
    }
}
