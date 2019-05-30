package Connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MenuHandler extends Display {
    
    
    public MenuHandler() {
        displaySelf();
        mainMenu = this;
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
        
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("hi");
        int xCoord = e.getX();
        int yCoord = e.getY();
        System.out.println(xCoord+" "+yCoord);
        
        if (xCoord-insets.left>250 && xCoord-insets.left<500) {
            if (Math.abs((yCoord-insets.top)-300)<25) { // PLAY button clicked
                System.out.println("playing...");
                closeSelf();
                Game game = new Game();
            } else if (Math.abs((yCoord-insets.top)-375)<25) { // Archived Games button clicked
                System.out.println("getting archived games...");
                closeSelf();
                GameListHandler gameListHandler = new GameListHandler();
            }
        }
    }
    
    public ArrayList<Game> getGames(){
        return games;
    }
    
    public void listGames() {
        // display the menu to pick games from
    }
    
}

/**
 * option 1: play => redirects to pickhandler
 * option 2: archived games => redirects to game list
 * option 3: settings => redirects to.. settings lol
 */

