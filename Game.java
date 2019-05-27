

import java.awt.Color;
import java.util.ArrayList;

/**
 * plays the game (the board object plays the game tbh)
 *
 */
public class Game
{
    private PickHandler pickHandler;
    private Display boardHandler;
    
    private Player player1;
    private Player player2;

    private Board board;

    public Game()
    {
        pickHandler = new PickHandler();
        pickHandler.pick();
        player1 = pickHandler.getPlayer1();
        player2 = pickHandler.getPlayer2();
        boardHandler = new BoardHandler(player1,player2);
        System.out.println("hmmm");
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
