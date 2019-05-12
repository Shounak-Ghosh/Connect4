package Connect4;

import java.awt.Color;

public class Tester {
    public static void main(String[] args) {
        Player p1 = new HumanPlayer("Player 1", Color.RED);
        Player p2 = new HumanPlayer("Player 2", Color.BLUE);
        Display d = new GameDisplay(p1, p2);        
    }
}
