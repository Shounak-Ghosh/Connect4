package Connect4;

import java.awt.Color;
import java.util.ArrayList;

public class GoHumanPlayer extends GoPlayer {
	private GoDisplay display;
	private GoBoard board;
	
	public GoHumanPlayer(String n, Color c, GoBoard goBoard, GoDisplay goDisplay)
    {
        super(n, c);
        board = goBoard;
        display = goDisplay;
    }
	
	public int[] nextMove()
    {
        int[] move = display.selectMove();
        while(board.isValidMove(move[0], move[1]))
        {
            move = display.selectMove();
        }
        return move;
    }
}
