package Connect4;

import java.awt.Color;

public class Tester
{
    public static void main(String[] args)
    {
        Player p1 = new HumanPlayer("Player 1", Color.YELLOW);
        Player p2 = new HumanPlayer("Player 2", Color.RED);
        Player r1 = new RandomPlayer("Random Player", Color.RED);
        
        
        Display d = new GameDisplay(p1, r1);
    }
}
