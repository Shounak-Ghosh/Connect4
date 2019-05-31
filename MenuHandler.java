package Connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MenuHandler extends Display {
    
    public MenuHandler() {
        loadGames(); // puts all games into local memory
        Noise.initializeSounds();
        displaySelf();
        mainMenu = this;
        Noise.startMenuMusic();
    }
    
    public void paintComponent(Graphics g)
    {
        paintEverything(g);
        frame.setTitle("Main Menu");
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
        System.out.println("main menu clicked");
        int xCoord = e.getX();
        int yCoord = e.getY();
        
        if (xCoord-insets.left>250 && xCoord-insets.left<500) {
            if (Math.abs((yCoord-insets.top)-300)<25) { // PLAY button clicked
                Noise.playButtonNoise();
                Noise.stopMenuMusic();
                closeSelf();
                System.out.println(games);
                Game game = new Game();
            } else if (Math.abs((yCoord-insets.top)-375)<25) { // Archived Games button clicked
                Noise.playButtonNoise();
                Noise.stopMenuMusic();
                closeSelf();
                try
                {
                    GameListHandler gameListHandler = new GameListHandler();
                } catch (FileNotFoundException e1)
                {
                }
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
