package Connect4;

import java.awt.Color;

public class HumanPlayer extends Player
{

    /**
     * Constructor: Creates a HumanPlayer object
     * 
     * @param n The name of the player
     * @param c The color of the player
     * @param d The BoardDisplay object used to display/update the board
     */
    public HumanPlayer(String n, Color c)
    {
        name = n;
        color = c;

    }
    
    public int getMove()
    {
        return 0;
    }

}
