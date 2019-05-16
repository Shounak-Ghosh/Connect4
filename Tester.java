package Connect4;

import java.awt.Color;
import java.util.*;

public class Tester
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        Player p1; // human player 1
        Player p2; // human player 2
        Player r1; // random player
        Player d1; // defensive player
        Display d;

//        System.out.println("Welcome to Connect 4: Choose between (1) PvP or (2) CvP.");
//        // precondition: user enters either 1 or 2
//        int choice = sc.nextInt();
//
//        if (choice == 1)
//        {
//            p1 = new HumanPlayer("Player 1", Color.YELLOW);
//            p2 = new HumanPlayer("Player 2", Color.RED);
//            System.out.print("Player 1's Name: ");
//            // precondition: user inputs String
//            String name = sc.next();
//            p1.setName(name);
//            //System.out.println();
//            System.out.print("Player 2's Name: ");
//            name = sc.next();
//            p2.setName(name);
//            d = new BoardHandler(p1, p2);
//            System.out.println(p1.name + "  " + p2.name);
//            
//            
//        }
//        else if (choice == 2)
//        {
//            System.out.println("Choose the level of Difficulty: (1) Easy (2) Medium (3) Hard");
//            // precondition: user enters 1, 2, or 3
//            choice = sc.nextInt();
//            if(choice == 1) 
//            {
//                p1 = new HumanPlayer("Player 1", Color.YELLOW);
//                r1 = new RandomPlayer("Random Player", Color.RED);
//                System.out.print("Player 1's Name: ");
//                // precondition: user inputs String
//                String name = sc.next();
//                p1.setName(name);
//                d = new BoardHandler(p1, r1);
//            }
//            else if(choice == 2) 
//            {
//                p1 = new HumanPlayer("Player 1", Color.YELLOW);
//                d1 = new DefensivePlayer("Defensive Player", Color.RED);
//                System.out.print("Player 1's Name: ");
//                // precondition: user inputs String
//                String name = sc.next();
//                p1.setName(name);
//                d = new BoardHandler(p1, d1);
//            }
//            else if(choice == 3) 
//            {
//                System.out.println("Work in progress. Try another level of difficulty.");
//                return;
//            }
//
//        }
//
//        r1 = new RandomPlayer("Random Player", Color.RED);

        //3d = new BoardHandler(new HumanPlayer("p1", Color.YELLOW), new HumanPlayer("p2", Color.RED));
        d1 = new DefensivePlayer("def. player", Color.RED);
        d = new BoardHandler(new HumanPlayer("human player", Color.YELLOW), d1);
    }

}
