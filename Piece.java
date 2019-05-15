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
    private Player player;
    
    private boolean highlighted;
    
    public Piece(Color type, Player player) 
    {
        color = type;
        this.player = player;
        highlighted = true;
    }

    /**
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }  
    
    /**
     * @return the piece's owner (player)
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Tests if this piece has the same color as another piece
     * (if they are on the same team)
     * @param otherPiece the other piece
     * @return true if have the same color, false otherwise
     */
    public boolean is(Piece otherPiece) {
        if (otherPiece==null) return false;
        return color.equals(otherPiece.color);
    }
    
    public void highlight(boolean isHighlighted) {
        highlighted = isHighlighted;
    }
    
    public boolean isHighlighted() {
        return highlighted;
    }
}
