package Connect4;

import java.awt.Color;

/**
 * 
 */
public abstract class GoPlayer
{
    private Color color;
    private String name;
    
    public GoPlayer(String name, Color color)
    {
    	this.name = name;
    	this.color = color;
    }

    public Color getColor()
    {
        return color;
    }
    
    public String getName()
    {
    	return name;
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }
    
    public String toString()
    {
        return name;
    }
    
    abstract int[] nextMove();
}