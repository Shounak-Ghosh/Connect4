package Connect4;

import java.awt.Color;

/**
 * 
 *
 *
 */
public class Piece
{
    private Color color;
    
    public Piece(Color type) 
    {
        color = type;
    }

    /**
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }    
}
