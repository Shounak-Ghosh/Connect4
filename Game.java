

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
    
    private Board board;

    public Game(Player p1, Player p2) {
        board = new Board(p1, p2);
        player1 = p1;
        player2 = p2;
    }

    /**
     * Displays and plays the game
     */
    public void play() {
        
    }

    /**
     * Executes the next move in the game
     */
    private void nextMove() {
        
    }
}
