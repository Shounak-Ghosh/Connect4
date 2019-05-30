package Connect4;

import java.awt.Color;

public class Connect4 
{
    
    
    
    // driver class for the connect4 app
    
    // the code inside serves as a tester for the actual game, without the main-menu code in the way
    public static void main(String[] args) 
    {
        Player d1; // defensive player
        Display d;
        
        d = new BoardHandler(new HumanPlayer("p1", Color.YELLOW), new HumanPlayer("p2", Color.RED));
        //d1 = new DefensivePlayer("def. player", Color.RED);
    }

}

  
