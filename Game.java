package Connect4;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

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


    public Game() {
        pickHandler = new PickHandler(this);
    }
    
    public Game(Player player1, Player player2, Stack<Integer> moves) {
        this.player1 = player1;
        this.player2 = player2;
        boardHandler = new BoardHandler(player1,player2, new Board(player1,player2,moves));
    }
    
    public void play() {
      System.out.println("playing...");
      player1 = pickHandler.getPlayer1();
      player2 = pickHandler.getPlayer2();
      pickHandler.getGames().add(this);
      System.out.println(pickHandler.getGames());
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
