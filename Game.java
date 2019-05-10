package Connect4;

import java.awt.Color;
import java.util.ArrayList;

/**
 * plays the game (the board object plays the game tbh)
 *
 */
public class Game
{
    private boolean isGameOver;
    private Display gameDisplay;
    private Player player1;
    private Player player2;

    public Game(Player p1, Player p2) {
        board = new Board(p1, p2);
        player1 = p1;
        player2 = p2;
    }

    /**
     * Displays and plays the game
     */
    public void play() {
        board.show();

        while (!isGameOver) // add checking for game over
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
