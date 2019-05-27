package Connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PickHandler extends Display
{
    
    private Player player1;
    private Player player2;

    public void pick() {
        // write this
        while(player1==null || player2==null) {
             
        }
        return;
//        player1 = new HumanPlayer("player1",Color.RED);
//        player2 = new HumanPlayer("player2",Color.YELLOW);
    }
    
    
    
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("hi");
        int xCoord = e.getX();
        int yCoord = e.getY();
        System.out.println(xCoord+" "+yCoord);
        
        if (xCoord-insets.left>250 && xCoord-insets.left<500) {
            if (Math.abs((yCoord-insets.top)-300)<25) { // PLAY button clicked
                System.out.println("playing...");
                removeSelf();
                Game game = new Game();
                games.add(game);
            } else if (Math.abs((yCoord-insets.top)-375)<25) { // Archived Games button clicked
                System.out.println("getting archived games...");
                removeSelf();
                GameListHandler gameListHandler = new GameListHandler();
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        System.out.println("repainting");
        
        games = new ArrayList<Game>();

        paintEverything(g);
    }
    
    private void paintEverything(Graphics g) {
        g.setColor(new Color(245,245,245));
        g.fillRect(250, 275, 250, 50);
        g.fillRect(250, 350, 250, 50);
        
        g.setColor(Color.black);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        g.drawString("PLAY", 325, 315);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        g.drawString("Archived Games", 280, 385);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

    }
    
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
}
