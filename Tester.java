package Connect4;

import java.awt.Color;

public class Tester
{
    public static void main(String[] args)
    {
        Player p1 = new HumanPlayer("Player 1", Color.YELLOW);
        Player p2 = new RandomPlayer("Player 2");
        Display d = new GameDisplay(p1, p2);
    }
}
