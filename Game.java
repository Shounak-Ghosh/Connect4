package Connect4;

import java.awt.Color;
import java.util.ArrayList;

/**
 * plays the game (the board plays the game tbh)
 *
 */
public class Game
{
    private PickHandler pickHandler;
    private Display boardHandler;
    
    private Player player1;
    private Player player2;

    private Board board;

    public Game() {
        pickHandler = new PickHandler(this);
    }
    
    public void play() {
      System.out.println("playing...");
      player1 = pickHandler.getPlayer1();
      player2 = pickHandler.getPlayer2();
      pickHandler.getGames().add(this);
      boardHandler = new BoardHandler(player1,player2);
    }
    
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
    
    public BoardHandler getBoardHandler() {
        return (BoardHandler)boardHandler;
    }

}
