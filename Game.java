package Connect4;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Represents 1 game. Wraps a BoardHandler and
 * keeps track of the players. Also takes
 * care of picking and reading in player
 * name, color, and/or difficulty.
 * @author Gloria Zhu, Shounak Ghosh
 * @version May 31 2019
 */
public class Game
{
    private PickHandler pickHandler;
    private Display boardHandler;
    
    private Player player1;
    private Player player2;
    
    
    // constructors

    public Game() {
        pickHandler = new PickHandler(this);
    }
    
    public Game(Player player1, Player player2, ArrayList<Integer> moves) {
        this.player1 = player1;
        this.player2 = player2;
        boardHandler = new BoardHandler(player1,player2, new Board(player1,player2,moves));
    }
    
    // methods to play
    public void initialize() {
        player1 = pickHandler.getPlayer1();
        player2 = pickHandler.getPlayer2();
    }
    
    public void play() {
        Noise.stopMenuMusic();

        boardHandler = new BoardHandler(player1, player2);
    }

    public void resume() {
        Noise.stopMenuMusic();
        boardHandler.displaySelf();
    }
    
    // getters
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public BoardHandler getBoardHandler() { return (BoardHandler)boardHandler; }
    public String toString() { return "GAME: "+player1+" "+player2; }
}
