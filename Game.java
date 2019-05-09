import java.awt.Color;
import java.util.ArrayList;

/**
 * The game of Connect4
 * 
 * @author Shounak Ghosh
 * @version 4.30.2019
 *
 */
public class Game
{
    private static boolean isGameOver;

    /**
     * Driver method; Sets up the board for play
     * 
     * @param args command-line argument
     */
    public static void main(String[] args)
    {
        Board board = new Board();
        isGameOver = false;

        
        // display the board
        BoardDisplay display = new BoardDisplay(board);

        


        display.showBoard();
    }

    /**
     * Plays a game between two players
     * 
     * @param board   The board to be played on
     * @param display The BoardDisplay object used to display/update the board
     * @param white   The white player; goes first
     * @param black   The black player
     */
    public static void play(Board board, BoardDisplay display, 
                            Player white, Player black)
    {
        while (!isGameOver) // remove later
        {
            nextTurn(board, display, white);
            nextTurn(board, display, black);
        }
        System.out.println("Game over.");
    }

    /**
     * Executes the next turn of a player
     * 
     * @param board   The board being played on
     * @param display The BoardDisplay object used to display/update the board
     * @param player  The player whose turn is being played out
     */
    private static void nextTurn(Board board,
                                 BoardDisplay display, Player player)
    {
        display.setTitle(player.getName());
        Move currentMove = player.nextMove();
        board.executeMove(currentMove);
        display.clearColors();

        if (currentMove == null)
        {
            isGameOver = true;
            return;
        }

        display.setColor(currentMove.getSource(), Color.CYAN);
        display.setColor(currentMove.getDestination(), Color.GREEN); 

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        display.clearColors();

    }
}
