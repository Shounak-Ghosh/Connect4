package Connect4;

import java.util.Stack;

/**
 * Represents the game board A game board handles all player actions
 */
public class Board
{
    private Piece[][] grid;
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    // private int lastColumn; // the last column that a piece was placed in
    private Stack<Integer> moves;

    /**
     * Creates a new Board
     * 
     * @param p1 player 1
     * @param p2 player 2
     */
    public Board(Player p1, Player p2)
    {
        grid = new Piece[6][7];
        this.p1 = p1;
        this.p2 = p2;
        currentPlayer = p1;
        // lastColumn = -1;
        moves = new Stack<Integer>();
    }

    /**
     * @param column the tested column
     * @return if the column has at least 1 empty slot
     */
    public boolean isValidMove(int column)
    {
        return (column >= 0 && grid[0][column] == null);
    }

    /**
     * @return the grid of pieces
     */
    public Piece[][] getPieces()
    {
        return grid;
    }

    public Player getPlayer1()
    {
        return p1;
    }

    public Player getPlayer2()
    {
        return p2;
    }

    /**
     * Moves a piece into the given column as far as it can go.
     * 
     * @precondition the column has at least 1 empty slot
     * @param column the given column
     */
    public void makeMove(int column)
    {
        int row = getTopmostEmptySlot(column);
        grid[row][column] = new Piece(currentPlayer.getColor(), currentPlayer);
        updateCurrentPlayer();
        moves.push(column);
        // lastColumn = column; // resets last column
    }

    // TODO: make a stack (integer) of all the past moves, max size is 42 so its ok
    // (no overflow)
    public void undo()
    {
        int index = 0;
        while (index < grid.length && grid[index][moves.peek()] == null)
        {
            index++;
        }
        grid[index][moves.peek()] = null;
        moves.pop();
    }

    private void updateCurrentPlayer()
    {
        if (currentPlayer == p1)
            currentPlayer = p2;
        else currentPlayer = p1;
    }

    public boolean isHumanTurn()
    {
        return currentPlayer instanceof HumanPlayer;
    }

    /**
     * @param column the tested column
     * @return the topmost empty slot in the given column
     * @precondition the column has @ least 1 empty slot/
     */
    private int getTopmostEmptySlot(int column)
    {
        int count = 0;
        while (count + 1 < grid.length && grid[count + 1][column] == null)
        {
            count++;
        }
        return count;
    }

    /**
     * REWRITE THIS - IT'S WRITTEN SO BADLY oml horizontal & vertical testing done,
     * TODO: diagonal...
     * 
     * @return if the winning player on the current board setup. if there is no
     *         winner, returns null.
     */
    public Player winner()
    {

        Player player = null;

        // testing horizontal
        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[0].length - 3; c++)
            {
                Piece test = grid[r][c];

                if (test != null)
                {
                    // test horizontally
                    if (test.is(grid[r][c + 1]) && test.is(grid[r][c + 2]) && test.is(grid[r][c + 3]))
                    {

                        test.highlight(true);
                        grid[r][c + 1].highlight(true);
                        grid[r][c + 2].highlight(true);
                        grid[r][c + 3].highlight(true);

                        player = test.getPlayer();
                    }
                }
            }
        }

        // testing vertical
        for (int r = 0; r < grid.length - 3; r++)
        {
            for (int c = 0; c < grid[0].length; c++)
            {
                Piece test = grid[r][c];

                if (test != null)
                {

                    // test vertically
                    if (test.is(grid[r + 1][c]) && test.is(grid[r + 2][c]) && test.is(grid[r + 3][c]))
                    {

                        test.highlight(true);
                        grid[r + 1][c].highlight(true);
                        grid[r + 2][c].highlight(true);
                        grid[r + 3][c].highlight(true);

                        player = test.getPlayer();
                    }
                }
            }
        }

        // test diagonal with positive slope
        for (int r = 3; r <= 5; r++)
        {
            for (int c = 0; c <= 3; c++)
            {
                Piece test = grid[r][c];

                if (test != null)
                {
                    if (test.is(grid[r - 1][c + 1]) && test.is(grid[r - 2][c + 2]) && test.is(grid[r - 3][c + 3]))
                    {

                        test.highlight(true);
                        grid[r - 1][c + 1].highlight(true);
                        grid[r - 2][c + 2].highlight(true);
                        grid[r - 3][c + 3].highlight(true);

                        player = test.getPlayer();
                    }
                }

            }
        }

        // test diagonally with negative slope
        for (int r = 3; r <= 5; r++)
        {
            for (int c = 3; c <= 6; c++)
            {
                Piece test = grid[r][c];

                if (test != null)
                {
                    if (test.is(grid[r - 1][c - 1]) && test.is(grid[r - 2][c - 2]) && test.is(grid[r - 3][c - 3]))
                    {

                        test.highlight(true);
                        grid[r - 1][c - 1].highlight(true);
                        grid[r - 2][c - 2].highlight(true);
                        grid[r - 3][c - 3].highlight(true);

                        player = test.getPlayer();
                    }
                }

            }
        }

        System.out.println("winner is " + player);

        return player;
    }

}
