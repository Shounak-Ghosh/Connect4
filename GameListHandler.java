package Connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

public class GameListHandler extends Display {
    
    public GameListHandler() throws FileNotFoundException {
        System.out.println("game list created");
        displaySelf();
    }
    
    public void paintComponent(Graphics g)
    {
        paintEverything(g);
    }
    
    private void paintEverything(Graphics g) {
        paintGrid(g);
        paintGames(g);
    }
    
    private void paintGrid(Graphics g) {
        g.setColor(Color.BLACK);
        
        for(int c=1;c<=2;c++) {
            for(int r=1;r<=3;r++) {
                g.drawRect(10*c+360*(c-1), 20*r+140*(r-1), 360, 140);
            }
        }
    }
    
    private void paintGames(Graphics g) {
        g.setColor(Color.WHITE);
        
        for(int i=1;i<=games.size() && i<=6;i++) {
            int c = (i+1)%2;
            int col = 10*(c+1)+360*c;
            int r = (i-1)/2;
            int row = 20*(r+1)+140*r;
            paintGame(g, games.get(games.size()-i), col, row);
        }
    }
    
    private void paintGame(Graphics g, Game game, int c, int r) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));

        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        
        g.drawString(player1.toString(),
                        c + 50, 
                        r + 40);
        
        g.drawString("  VS", c + 50, r + 75);
        
        g.drawString(player2.toString(), c + 50, r + 110);
        
        g.setColor(player1.getColor());
        g.fillOval(c + 20, r + 20, 20, 20);
        
        g.setColor(player2.getColor());
        g.fillOval(c + 20, r + 90, 20, 20);
        
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
        g.setColor(Color.BLACK);
        g.drawString("PLAY",c+247, r+80);
        g.drawRect(c + 230, r + 20, 100, 100);
    }
    
    public void mouseClicked(MouseEvent e)
    {
        int xCoord = e.getX();
        int yCoord = e.getY();
        
        for(int i=1;i<=games.size() && i<=6;i++) {
            int c = (i+1)%2;
            int col = 10*(c+1)+360*c + 280;
            int r = (i-1)/2;
            int row = 20*(r+1)+140*r + 70;
            
            if (Math.abs(col-xCoord)<=50 && Math.abs(row-yCoord)<=50) {
                Noise.playButtonNoise();
                closeSelf();
                games.get(i-1).resume();
            }
        }
        
    } 
    
    
    
}
