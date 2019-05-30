package Connect4;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameListHandler extends Display
{
    public GameListHandler() {
        displaySelf();
    }
    
    public void paintComponent(Graphics g)
    {
        paintEverything(g);
    }
    
    private void paintEverything(Graphics g) {
        paintGames(g);
    }
    
    private void paintGames(Graphics g) {
        for(int c=0;c<2;c++) {
//            for
        }
    }
}
