package Connect4;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public abstract class Display extends JComponent implements MouseListener
{
//    protected Board board;

    protected JFrame frame;

    protected final Color BACKGROUND_COLOR = new Color(192, 192, 192);
    protected final Color GRID_COLOR = Color.BLUE;
    protected final Color HIGHLIGHT_COLOR = new Color(0, 0, 153);
    protected final Color TEXT_COLOR = Color.WHITE;

    // insets of the frame (platform-dependent)
    protected Insets insets;
    
    protected static ArrayList<Game> games;
    
    protected static Display mainMenu;

    public Display()
    {
        frame = new JFrame();
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                storeGames();
            }
        };
        frame.addWindowListener(exitListener);
    }
    
    public ArrayList<Game> getGames() {
        return games;
    }
    
    private void storeGames() {
        File file = new File(getClass().getResource("games.txt").getPath());

        try
        {
            file.createNewFile();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            
            System.out.println("saving games");
            System.out.println(games);
            for (Game g: games) {
                
                // writing 0 for human player & the player's name/color
                writer.write("0");
                writer.write(g.getPlayer1().toString());
                writer.write(";");
                writer.write(g.getPlayer1().getColor().getRGB());
                writer.newLine();
                
                // writing the player's type, name, and color
                Player player2 = g.getPlayer2();
                if (player2 instanceof RandomPlayer) writer.write("1");
                else if (player2 instanceof DefensivePlayer) writer.write("2");
//                else if (player2 instanceof SmartPlayer) writer.write("3");
                else writer.write("0");
                writer.write(g.getPlayer2().toString());
                writer.write(";");
                writer.newLine();
                
                Stack<Integer> moves = g.getBoardHandler().getBoard().getMoves();
                while(!moves.isEmpty()) {
                    writer.write(moves.pop().toString());
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        
    }
    
    protected void removeSelf() {
        frame.getContentPane().remove(this);
    }
    
    protected void displaySelf() {
        frame.getContentPane().add(this); // very important line of code reee

        frame.setResizable(false);
        frame.pack();
        insets = frame.getInsets();

        frame.setSize(750 + insets.left + insets.right, 520 + insets.top + insets.right);
        frame.setLocation(200, 100);
        frame.setTitle("Connect 4");
        Container c = frame.getContentPane();
        c.setBackground(BACKGROUND_COLOR);

        System.out.println(frame.getHeight());

        frame.setVisible(true);

        frame.addMouseListener(this);

        repaint();
    }
    
    public void closeSelf() {
        frame.dispose();
    }

    // this method should be overriden
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

// accepts listeners - get x/y coordinates to determine what coordinate was clicked
