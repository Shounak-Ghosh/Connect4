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

    /**
     * Creates a new Board
     * @param p1 player 1
     * @param p2 player 2
     */
    public Board(Player p1, Player p2) {
        grid = new Piece[6][7];
        this.p1 = p1;
        this.p2 = p2;
        currentPlayer = p1;
    }
    
    /**
     * @param column the tested column
     * @return if the column has at least 1 empty slot
     */
    public boolean isValidMove(int column) {
        return grid[0][column] == null;
    }
    
    /**
     * @return the grid of pieces
     */
    public Piece[][] getPieces() {
        return grid;
    }
    
    /**
     * Moves a piece into the given column as far as it can go.
     * @precondition the column has at least 1 empty slot
     * @param column the given column
     */
    public void makeMove(int column) {
        int row = getTopmostEmptySlot(column);
        grid[row][column] = new Piece(currentPlayer.getColor());
    }
    
    /**
     * @param column the tested column
     * @return the topmost empty slot in the given column
     * @precondition the column has @ least 1 empty slot/
     */
    private int getTopmostEmptySlot(int column) {
        int count = 0;
        while(count+1<grid.length && grid[count+1][column]==null) {
            count++;
        }
        return count;
    }
    
    /**
     * WRITE THIS---------------------------------------------------------------
     * 
     * @return if the winning player on the current board setup.
     *         if there is no winner, returns null.
     */
    private Player winner() {
        return null;
    }
       
}
