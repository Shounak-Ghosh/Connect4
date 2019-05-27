package Connect4;

import java.awt.Color;

/**
 * 
 */
public abstract class Player
{
    protected Color color;
    protected String name;

    public Color getColor()
    {
        return color;
        
    }

    public String printColor() 
    {
        if(Color.RED == color) 
        {
            return "Red";
        }
        else if (Color.YELLOW == color) 
        {
            return "Yellow";
        }
        return color.toString();
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }
    
    public String toString()
    {
        return name;
    }
}
