package Connect4;

import java.awt.Color;

/**
 * 
 *
 *
 */
public class GoPiece
{
    private Color color;
    private GoPlayer player;
    
    private boolean highlighted;
    
    public GoPiece(Color type, GoPlayer player) 
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
    public GoPlayer getPlayer() {
        return player;
    }
    
    /**
     * Tests if this piece has the same color as another piece
     * (if they are on the same team)
     * @param otherPiece the other piece
     * @return true if have the same color, false otherwise
     */
    public boolean is(GoPiece otherPiece) {
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