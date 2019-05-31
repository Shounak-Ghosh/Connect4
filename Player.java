package Connect4;

import java.awt.Color;

/**
 * The abstract Player class is the superclass of all
 * the other player classes.
 * @author Gloria Zhu, Shounak Ghosh
 * @version May 31 2019
 *
 */
public abstract class Player
{
    protected Color color;
    protected String name;

    public Color getColor() { return color; }

    public void setName(String name) { this.name = name; }
    
    public String toString() { return name+"-"+color.toString(); }
}
