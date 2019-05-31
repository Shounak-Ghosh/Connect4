package Connect4;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * The abstract superclass for all displays in the project. Makes setting sizes,
 * behavior, keeping constant colors, determining insets, etc. easier. Also is
 * in charge of storing game information and reading it back in from the text
 * file (since all displays will need to do these methods)
 * 
 * @author Gloria Zhu
 * @author Shounak Ghosh
 *
 */
public abstract class Display extends JComponent implements MouseListener
{
    // not sure what this is for, but I guess it's required
    // for extending a jcomponent
    private static final long serialVersionUID = 1L;

    protected JFrame frame;

    protected final Color BACKGROUND_COLOR = new Color(192, 192, 192);
    protected final Color GRID_COLOR = new Color(125, 125, 125);
    protected final Color HIGHLIGHT_COLOR = Color.black;
    protected final Color TEXT_COLOR = Color.WHITE;

    // insets of the frame (platform-dependent)
    protected Insets insets;

    // static variables (same for all displays)
    public static ArrayList<Game> games;
    protected static Display mainMenu;

    public Display()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // stores games upon exiting
        WindowListener exitListener = new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                Noise.playButtonNoise();
                Noise.stopMenuMusic();
                storeGames();
            }
        };
        frame.addWindowListener(exitListener);
    }

    public ArrayList<Game> getGames()
    {
        return games;
    }

    // storing/reading in games

    public void storeGames()
    {
        String workingDir = System.getProperty("user.dir");

        Path filePath = Paths.get(workingDir + File.separator + "sampleFile.txt");

        File file = new File(filePath.toString());

        try
        {
            // wiping the file
            PrintWriter wiper = new PrintWriter(file);
            wiper.print("");
            wiper.close();

            file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            System.out.println(games);
            for (int i = games.size() - 1; i >= 0; i--)
            {

                Game g = games.get(i);

                // writing 0 for human player & the player's name/color
                writer.write("0");
                writer.write(g.getPlayer1().toString());
                writer.write(";");
                writer.write(String.valueOf(g.getPlayer1().getColor().getRGB()));
                writer.newLine();

                // writing the player's type, name, and color
                Player player2 = g.getPlayer2();
                if (player2 instanceof RandomPlayer)
                    writer.write("1");
                else if (player2 instanceof DefensivePlayer)
                    writer.write("2");
//                else if (player2 instanceof SmartPlayer) writer.write("3");
                else writer.write("0");
                writer.write(g.getPlayer2().toString());
                writer.write(";");
                writer.write(String.valueOf(player2.getColor().getRGB()));
                writer.newLine();

                Stack<Integer> moveStack = g.getBoardHandler().getBoard().getMoves();
                ArrayList<Integer> moves = new ArrayList<Integer>(moveStack);
                for (int j = 0; j < moves.size(); j++)
                {
                    writer.write(String.valueOf(moves.get(moves.size() - 1 - j)));
                }
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("games stored: " + games);

    }

    public void loadGames()
    {
        games = new ArrayList<Game>();
        try
        {
            String workingDir = System.getProperty("user.dir");

            Path filePath = Paths.get(workingDir + File.separator + "sampleFile.txt");

            File file = new File(filePath.toString());

            if (file.createNewFile())
                return;

            BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));

            ArrayList<String> player1name = new ArrayList<String>();
            ArrayList<Color> player1color = new ArrayList<Color>();

            ArrayList<Integer> player2type = new ArrayList<Integer>();
            ArrayList<String> player2name = new ArrayList<String>();
            ArrayList<Color> player2color = new ArrayList<Color>();

            ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();

            int count = 0;
            for (String line; (line = br.readLine()) != null;)
            {
                if (count == 6)
                    break;
                String player1info = line;
                process(player1name, player1color, player1info);
                String player2info = br.readLine();
                process(player2name, player2color, player2info, player2type);

                moves.add(new ArrayList<Integer>());

                String moveSet = br.readLine();
                for (int i = 0; i < moveSet.length(); i++)
                {
                    int move = Integer.valueOf(moveSet.substring(i, i + 1));
                    System.out.println("pushed move " + move + " to i");
                    moves.get(count).add(move);
                }
                count++;
            }

            br.close();

            for (int i = 0; i < count; i++)
            {
                Player p1 = new HumanPlayer(player1name.get(i), player1color.get(i));
                Player p2 = new HumanPlayer(player2name.get(i), player2color.get(i)); // change this after getting smart
                                                                                      // player
                int p2type = player2type.get(i);
                if (p2type == 0)
                    p2 = new HumanPlayer(player2name.get(i), player2color.get(i));
                else if (p2type == 1)
                    p2 = new RandomPlayer(player2name.get(i), player2color.get(i));
                else if (p2type == 2)
                    p2 = new DefensivePlayer(player2name.get(i), player2color.get(i));
//                else if (p2type==3) p2 = new SmartPlayer(player2name.get(i),

                games.add(new Game(p1, p2, moves.get(i)));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("games loaded: " + games);
    }

    // process 1 line of player info

    private void process(ArrayList<String> names, ArrayList<Color> colors, String info, ArrayList<Integer> types)
    {
        process(names, colors, info);
        types.add(0, Integer.valueOf(info.substring(0, 1)));
    }

    private void process(ArrayList<String> names, ArrayList<Color> colors, String info)
    {
        int semi = info.indexOf(";");
        names.add(0, info.substring(1, semi));
        colors.add(0, new Color(Integer.valueOf(info.substring(semi + 1))));

    }

    // display itself
    protected void displaySelf()
    {
        frame.getContentPane().add(this); // very important line of code reee

        frame.setResizable(false);
        frame.pack();
        insets = frame.getInsets();

        frame.setSize(750 + insets.left + insets.right, 520 + insets.top + insets.right);
        frame.setLocation(200, 100);
        frame.setTitle("Connect 4");
        Container c = frame.getContentPane();
        c.setBackground(BACKGROUND_COLOR);

        frame.setVisible(true);

        // removing previous mouse listeners so mouse events
        // aren't triggered twice
        for (int i = 0; i < frame.getMouseListeners().length; i++)
        {
            frame.removeMouseListener(frame.getMouseListeners()[i]);
        }
        frame.addMouseListener(this);

        repaint();
    }

    // close itself
    public void closeSelf()
    {
        storeGames();
        frame.dispose();
    }

    // this method should be overridden
    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }
}
