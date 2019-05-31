package Connect4;

import java.awt.Color;
import java.util.ArrayList;

/**
 * plays the game (the board object plays the game tbh)
 *
 */
public class GoGame
{
//	private Piece[][] grid;
//    private boolean isGameOver;
//    private Display gameDisplay;
//    private Player player1;
//    private Player player2;
//
//    private GoBoard board;

//    public GoGame(Player p1, Player p2)
//    {
//        board = new GoBoard(p1, p2);
//        player1 = p1;
//        player2 = p2;
//        grid = new Piece[19][19];
//    }

    /**
     * Displays and plays the game
     */
    public static void play(GoBoard board, GoDisplay display, GoPlayer white, GoPlayer black)
    {
    	while(true)//replace with win condition
        {
            nextTurn(board, display, white);
            nextTurn(board, display, black);
        }
    }

    /**
     * Executes the next move in the game
     */
    private static void nextTurn(GoBoard board, GoDisplay display, GoPlayer player)
    {
    	display.setTitle(player.getName());
    	int[] move = player.nextMove();
    	board.executeMove(move, player);
    	//display.displaySelf();
    	try
        {
            Thread.sleep(500);
        }
        catch(InterruptedException e)
        {
            
        }
    }
    
    public static void main(String[] args)
    {
    	GoBoard board = new GoBoard();
    	GoDisplay display = new GoDisplay(board);
    	GoHumanPlayer player1 = new GoHumanPlayer("Player 1", Color.WHITE, board, display);
    	//change player2 to a SmartPlayer or smth to play against a computer
    	GoHumanPlayer player2 = new GoHumanPlayer("Player 2", Color.BLACK, board, display);
    	play(board, display, player1, player2);
    }
}
