import java.awt.Color;

/**
 * 
 *
 *
 */
public class Piece
{
    private Board board;
    
    private Color color;
    
    private Location location;
    
    private String imageFileName;
    
    public Piece(Color type, String filename) 
    {
        color = type;
        imageFileName = filename;
    }
    
    
    /**
     * Checks if a given Location is valid
     * @param dest the Location to be checked
     * @return true if the Location is valid;
     *         false otherwise
     */
    public boolean isValidDestination(Location dest)
    {
        if (board.isValid(dest))
        {
            return board.get(dest) == null;
        }
        return false;
    }
    
    // returns the board this piece is on
    public Board getBoard()
    {
        return board;
    }

    // returns the location of this piece on the board
    public Location getLocation()
    {
        return location;
    }

    // returns the color of this piece
    public Color getColor()
    {
        return color;
    }

    // returns the name of the file used to display this piece
    public String getImageFileName()
    {
        return imageFileName;
    }

    
}
