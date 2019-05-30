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
        System.out.println("game list created");
        displaySelf();
        loadGames();
    }
    
    public void paintComponent(Graphics g)
    {
        try
        {
            loadGames();
        } catch (FileNotFoundException e) { e.printStackTrace(); }
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
            int r = i/2;
            int row = 20*(r+1)+140*r;
            paintGame(g, games.get(i-1), col, row);
        }
    }
    
    private void paintGame(Graphics g, Game game, int c, int r) {
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
        g.fillOval(c + 20, 20*(r+1)+140*r + 90, 20, 20);
        
        g.setColor(Color.BLACK);
        g.drawRect(c + 20, r + 20, 100, 100);
    }
    
    private void loadGames() throws FileNotFoundException {
        games = new ArrayList<Game>();
        try {
            String workingDir = System.getProperty("user.dir");

            Path filePath = Paths.get(workingDir+File.separator+"sampleFile.txt");

            File file = new File(filePath.toString());

            if (file.createNewFile()) return;

            BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));

            ArrayList<String> player1name = new ArrayList<String>();
            ArrayList<Color> player1color = new ArrayList<Color>();
            
            ArrayList<Integer> player2type = new ArrayList<Integer>();
            ArrayList<String> player2name = new ArrayList<String>();
            ArrayList<Color> player2color = new ArrayList<Color>();
            
            ArrayList<Stack<Integer>> moves = new ArrayList<Stack<Integer>>();

            int count = 0;
            for (String line; (line = br.readLine()) != null;) {
                if (count==6) break;
                String player1info = line;
                process(player1name, player1color,player1info);
                String player2info = br.readLine();
                process(player2name,player2color,player2info,player2type);
                
                moves.add(new Stack<Integer>());
                
                String moveSet = br.readLine();
                for(int i=0;i<moveSet.length();i++) {
                    int move = Integer.valueOf(moveSet.substring(i,i+1));
                    moves.get(count).push(move);                }
                count++;
            }
            
            for(int i=0;i<count;i++) {
                Player p1 = new HumanPlayer(player1name.get(i),
                                       player1color.get(i));
                Player p2 = new HumanPlayer(player2name.get(i),
                        player2color.get(i)); // change this after getting smartplayer
                int p2type = player2type.get(i);
                if (p2type==0) p2 = new HumanPlayer(player2name.get(i),
                                        player2color.get(i));
                else if (p2type==1) p2 = new RandomPlayer(player2name.get(i),
                                        player2color.get(i));
                else if (p2type==2) p2 = new DefensivePlayer(player2name.get(i),
                                        player2color.get(i));
//                else if (p2type==3) p2 = new SmartPlayer(player2name.get(i),
                        
                games.add(new Game(p1,p2,moves.get(i)));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private void process(ArrayList<String> names,ArrayList<Color> colors, String info,
                    ArrayList<Integer> types) {
        process(names,colors,info);
        types.add(Integer.valueOf(info.substring(0,1)));
    }
    
    private void process(ArrayList<String> names,ArrayList<Color> colors, String info) {
        int semi = info.indexOf(";");
        names.add(info.substring(1,semi));
        colors.add(new Color(Integer.valueOf(info.substring(semi+1))));

    }
    
}
