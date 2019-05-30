package Connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
        displaySelf();
        loadGames();
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
        g.setColor(Color.BLACK);
        
        for(int i=0;i<games.size() && i<6;i++) {
            int col = i%2;
            int row = i/2;
            paintGame(g, games.get(i), col, row);
        }
    }
    
    private void paintGame(Graphics g, Game game, int c, int r) {
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 19));
        g.drawString(game.getPlayer1().toString(),
                        10*c+360*(c-1), 20*r+140*(r-1));
    }
    
    private void loadGames() throws FileNotFoundException {
        try {
            System.out.println("yo");
            String workingDir = System.getProperty("user.dir");

            Path filePath = Paths.get(workingDir+File.separator+"sampleFile.txt");

            File file = new File(filePath.toString());

            if (file.createNewFile()) return;
            System.out.println("yo2");

            BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));

            ArrayList<String> player1name = new ArrayList<String>();
            ArrayList<Color> player1color = new ArrayList<Color>();
            ArrayList<String> player2name = new ArrayList<String>();
            ArrayList<Color> player2color = new ArrayList<Color>();
            
            ArrayList<Stack<Integer>> moves = new ArrayList<Stack<Integer>>();

            int count = 0;
            for (String line; (line = br.readLine()) != null;) {
                if (count==6) break;
                String player1info = line;
                process(player1name, player1color,player1info);
                String player2info = br.readLine();
                process(player2name,player2color,player2info);
                
                moves.add(new Stack<Integer>());
                
                String moveSet = br.readLine();
                for(int i=0;i<moveSet.length();i++) {
                    int move = Integer.valueOf(moveSet.substring(i,i+1));
                    moves.get(count).push(move);
                    System.out.println(move);
                }
                count++;
            }
            
            System.out.println(player1name);
            System.out.println(player1color);
            System.out.println(moves);
            
            System.out.println("done");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private void process(ArrayList<String> names,ArrayList<Color> colors, String info) {
        int semi = info.indexOf(";");
        names.add(info.substring(1,semi));
        colors.add(new Color(Integer.valueOf(info.substring(semi+1))));

    }
}
