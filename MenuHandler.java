package Connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * A MenuHandler is in charge of handling the main menu.
 * It is the starting screen upon running the application
 * and is in charge of initializing things (loading in initial 
 * games, initializing sounds, etc.)
 * It can navigate to the picking and playing screens or the
 * archived games list.
 * @author Gloria Zhu, Shounak Ghosh
 * @version May 31 2019
 *
 */
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
        Noise.startMenuMusic();
        paintEverything(g);
        frame.setTitle("Main Menu");
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 80));
        g.drawString("CONNECT 4", 155, 200);
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
        
    public void mouseClicked(MouseEvent e) {
        int xCoord = e.getX();
        int yCoord = e.getY();
        
        if (xCoord-insets.left>250 && xCoord-insets.left<500) {
            if (Math.abs((yCoord-insets.top)-300)<25) { // PLAY button clicked
                Noise.playButtonNoise();
                closeSelf();
                Game game = new Game();
            } else if (Math.abs((yCoord-insets.top)-375)<25) { // Archived Games button clicked
                Noise.playButtonNoise();
                closeSelf();
                try { GameListHandler gameListHandler = new GameListHandler(); } 
                catch (FileNotFoundException e1) {}
            }
        }
    }
    
    public ArrayList<Game> getGames(){ return games; }
}
