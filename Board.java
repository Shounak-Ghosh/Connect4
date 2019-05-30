package Connect4;

import java.awt.Color;
import java.util.Stack;

//test growl
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
        System.out.println("Reached Board Constructor");
    }

    public Stack<Integer> getMoves()
    {
        return moves;
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

    public void setCurrentPlayer(Player p)
    {
        currentPlayer = p;
    }

    
    public void animateMove(int column, int row) 
    {
        Piece p = new Piece(currentPlayer.getColor(), currentPlayer);
        grid[row][column] = p;
        
    }
    
    public void removeAnimatedMove(int column, int row)
    {
        grid[row][column] = null;
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
        Piece p = new Piece(currentPlayer.getColor(), currentPlayer);
        grid[0][column] = p;
        // THREAD.SLEEP CODE USED TO BE HERE
        grid[0][column] = null;
        grid[row][column] = p;

        updateCurrentPlayer();

        // lastColumn = column; // resets last column
        if (!moves.isEmpty())
        {
            int unhighlightRow = getTopmostEmptySlot(moves.peek()) + 1;
            if (moves.peek() == column)
                unhighlightRow++;

            grid[unhighlightRow][moves.peek()].highlight(false);
        }
        moves.push(column);
    }

    public void makeTempMove(int column, Color c)
    {
        int row = getTopmostEmptySlot(column);
        System.out.println("current player " + currentPlayer);
        grid[row][column] = new Piece(c, currentPlayer);

        System.out.println(currentPlayer.printColor());
        moves.push(column);
        System.out.println("tempMove " + moves);
    }

    /**
     * Undos the last move on the board
     */
    public void undo()
    {
        if (moves.isEmpty())
            return;

        int index = getTopmostEmptySlot(moves.peek()) + 1;
        grid[index][moves.pop()] = null;

        updateCurrentPlayer();
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

    public void restart()
    {
        moves = new Stack<Integer>();
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                grid[i][j] = null;
            }
        }

    }

    /**
     * @param column the tested column
     * @return the topmost empty slot in the given column
     * @precondition the column has @ least 1 empty slot/
     */
    protected int getTopmostEmptySlot(int column)
    {
        int count = -1;
        while (count + 1 < grid.length && grid[count + 1][column] == null)
        {
            count++;
        }
        return count;
    }

    /**
     * REWRITE THIS - IT'S WRITTEN SO BADLY oml
     * 
     * @return if the winning player on the current board setup. if there is no
     *         winner, returns null.
     */
    public Player winner()
    {

        Player player = null;

        if (moves.size() >= 41)
        {
            System.out.println("DRAW");
            return null;
        }

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
