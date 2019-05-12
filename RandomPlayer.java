package Connect4;

import java.awt.Color;

public class RandomPlayer extends Player
{
    public RandomPlayer(String name) 
    {
        super.name = name;
        super.color = Color.red;
    }
    
    public int getMove() 
    {
       int move = (int) (Math.random() * 7);
       System.out.println(move);
       return move;
    }
}
