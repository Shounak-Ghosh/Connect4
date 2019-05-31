package Connect4;

import java.awt.Color;

/**
 * A Piece represents a piece. It keeps track of its color
 * and stores a reference to its player.
 * @author Gloria Zhu, Shounak Ghosh
 * @version May 31 2019
 *
 */
public class Piece
{
    private Color color;
    private boolean highlighted;
    private Player player;
    
    public Piece(Color type, Player player) {
        color = type;
        highlighted = true;
        this.player = player;
    }
    
    /**
     * Tests if this piece has the same color as another piece
     * (if they are on the same team)
     */
    public boolean is(Piece otherPiece) {
        if (otherPiece==null) return false;
        return color.equals(otherPiece.color);
    }
    
    public void highlight(boolean isHighlighted) { highlighted = isHighlighted; }
    public boolean isHighlighted() { return highlighted; }
    public Color getColor() { return color; }
    public Player getPlayer() { return player; }
}