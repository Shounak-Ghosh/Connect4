package Connect4;

/**
 * Represents the game board
 * A game board handles all player actions
 */
public class Board
{
    private Piece[][] grid;
    private Player p1;
    private Player p2;
    private Player currentPlayer;

    public Board(Player p1, Player p2) {
        grid = new Piece[6][7];
        this.p1 = p1;
        this.p2 = p2;
        currentPlayer = p1;
    }
    
    public boolean isValidMove(int column) {
        return grid[0][column] == null;
    }
    
    /**
     * WRITE THIS
     * Insert a piece and move it down until it interferes with something else
     * @param column
     */
    public void makeMove(int column) {
    }


   
}
