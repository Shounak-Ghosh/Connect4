package Connect4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

// 
/**
 * Used to display the contents of a game board
 * @author Michael Tran
 * @version 2019.5.28
 */
public class GoDisplay extends JComponent implements ActionListener
{
    private GoBoard board;
    private JButton[][] grid;
    private GoPiece selectedPiece;
    private int[] selectedMove;
    private JFrame frame;
    private Color[][] colors;

    /**
     * 
     * Constructs a new display for displaying the given board
     * @param board the board
     */ 
    public GoDisplay(GoBoard board)
    {
        this.board = board;
        grid = new JButton[19][19];
        colors = new Color[19][19];

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        //Wait until display has been drawn
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(19, 19));

        //Create each square as a button.
        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid[row].length; col++)
            {
                grid[row][col] = new JButton();
                grid[row][col].setOpaque(true);
                if ((row + col) % 2 == 0)
                    grid[row][col].setBackground(new Color(155, 145, 115));
                else
                    grid[row][col].setBackground(new Color(110, 85, 55));
                grid[row][col].setPreferredSize(new Dimension(50, 50));
                grid[row][col].setActionCommand(row + "," + col);
                grid[row][col].addActionListener(this);
                frame.getContentPane().add(grid[row][col]);
            }

        //Show the pieces
        showBoard();

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * 
     *Called when a square is clicked.
     * @param event the event
     */
    public void actionPerformed(ActionEvent event)
    {
        //Determine location of clicked button.
        String command = event.getActionCommand();
        int comma = command.indexOf(",");
        int row = Integer.parseInt(command.substring(0, comma));
        int col = Integer.parseInt(command.substring(comma + 1));
        int[] loc = {row, col};

        if (selectedPiece == null)
        {
            //we have just selected a piece for the first time.
            selectedPiece = board.get(loc);
            clearColors();
//            if (selectedPiece != null)
//            {
//                setColor(loc, Color.YELLOW);
//                for(Location location: selectedPiece.destinations())
//                {
//                    setColor(location, Color.YELLOW);
//                }
//            }
        }
        else if (loc.equals(selectedPiece.getLocation()))
        {
            //we are deselecting the piece
            selectedPiece = null;
            selectedMove = null;
            clearColors();
        }
        else if(board.get(loc)!=null&&selectedPiece.getColor().equals(board.get(loc).getColor()))
        {
            selectedPiece = board.get(loc);
            clearColors();
//            if (selectedPiece != null)
//            {
//                //setColor(loc, Color.YELLOW);
//                ArrayList<Location> lo = selectedPiece.destinations();
//                Iterator<Location> it = lo.iterator();
//                while(it.hasNext())
//                {
//                    setColor(it.next(), Color.YELLOW);
//                }
//            }
        }
        else
        {
            //we have selected a move
        	selectedMove = loc;
            //selectedMove = new Move(selectedPiece, loc);
            //setColor(loc, Color.YELLOW);
        }

        //         ArrayList<Move> locas = board.allMoves(Color.BLACK);
        //         Iterator<Move> it = locas.iterator();
        //         while(it.hasNext())
        //         {
        //             setColor(it.next().getDestination(), Color.YELLOW);
        //         }
    }

    /**
     * 
     *Redraws the board to include the pieces and border colors.
     */
    public void showBoard()
    {
        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid[row].length; col++)
            {
                int[] loc = {row, col};

                GoPiece piece = board.get(loc);

                Icon icon = null;
                if (piece != null)
                {
                    //System.out.println(loc);
                    grid[row][col].setForeground(piece.getColor());
                    icon = new ImageIcon(piece.getImageFileName());
                }
                grid[row][col].setIcon(icon);

                Color color = colors[row][col];

                if (color == null)
                    grid[row][col].setBorder(null);
                else
                    grid[row][col].setBorder(BorderFactory.createLineBorder(color));
            }
    }

    /**
     * 
     * Waits for the user to select a move and returns this move.
     * @return the seleceted move
     */
    public int[] selectMove()
    {
        try
        {
            selectedPiece = null;
            selectedMove = null;
            while (selectedMove == null)
                Thread.sleep(1);
            int[] move = selectedMove;
            selectedPiece = null;
            selectedMove = null;
            return move;
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    /**
     * 
     *Sets the title of the window.
     *@param title the title
     */
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    /**
     * 
     * Sets the color of the border for the given location, and redraws it.
     * @param loc the location
     * @param color the color
     */
    public void setColor(Location loc, Color color)
    {
        colors[loc.getRow()][loc.getCol()] = color;
        showBoard();
    }

    /**
     * 
     * Clears all border colors and redraws the board.
     */
    public void clearColors()
    {
        for (int row = 0; row < colors.length; row++)
            for (int col = 0; col < colors[row].length; col++)
                colors[row][col] = null;
        showBoard();
    }
    
//    protected void displaySelf() {
//      frame.getContentPane().add(this); // very important line of code reee
//
//      frame.setResizable(false);
//      frame.pack();
//      insets = frame.getInsets();
//
//      frame.setSize(750 + insets.left + insets.right, 520 + insets.top + insets.right);
//      frame.setLocation(200, 100);
//      frame.setTitle("Go");
//      Container c = frame.getContentPane();
//      c.setBackground(BACKGROUND_COLOR);
//
//      System.out.println(frame.getHeight());
//
//      frame.setVisible(true);
//
//      frame.addMouseListener(this);
//
//      repaint();
//  }
}



//package Connect4;
//
//
//import java.awt.*;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Stack;
//
//import javax.swing.JComponent;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.SwingUtilities;
//
//public  class GoDisplay extends JComponent implements MouseListener
//{
////    protected Board board;
//
//    protected static JFrame frame = new JFrame();
//
//    protected final Color BACKGROUND_COLOR = new Color(0,0,0);
//    protected final Color GRID_COLOR = Color.BLACK;
//    protected final Color HIGHLIGHT_COLOR = new Color(0, 0, 153);
//    protected final Color TEXT_COLOR = Color.BLACK;
//    
//    // insets of the frame (platform-dependent)
//    protected Insets insets;
//    
//    protected ArrayList<Game> games;
//    
//    protected static Display mainMenu;
//
//    public GoDisplay()
//    {
//        WindowListener exitListener = new WindowAdapter() {
//
//            @Override
//            public void windowClosing(WindowEvent e) {
////                storeGames();
//            }
//        };
//        frame.addWindowListener(exitListener);
//    }
//    
//    private void storeGames() {
//        String workingDir = System.getProperty("user.dir");
//
//        Path filePath = Paths.get(workingDir+File.separator+"sampleFile.txt");
//        
//        File file = new File(filePath.toString());
//
//        try
//        {
//            file.createNewFile();
//            System.out.println(filePath);
//            System.out.println(file);
//            
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
//            
//            for (Game g: games) {
//                writer.write(g.getPlayer1().toString());
//                writer.newLine();
//                writer.write(g.getPlayer2().toString());
//                writer.newLine();
//                
//                Stack<Integer> moves = g.getBoardHandler().getBoard().getMoves();
//                while(!moves.isEmpty()) {
//                    writer.write(moves.pop());
//                }
//                writer.newLine();
//            }
//            writer.close();
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        
//        
//    }
//    
//    protected void removeSelf() {
//        frame.getContentPane().remove(this);
//    }
//    
//    protected void displaySelf() {
//        frame.getContentPane().add(this); // very important line of code reee
//
//        frame.setResizable(false);
//        frame.pack();
//        insets = frame.getInsets();
//
//        frame.setSize(750 + insets.left + insets.right, 520 + insets.top + insets.right);
//        frame.setLocation(200, 100);
//        frame.setTitle("Go");
//        Container c = frame.getContentPane();
//        c.setBackground(BACKGROUND_COLOR);
//
//        System.out.println(frame.getHeight());
//
//        frame.setVisible(true);
//
//        frame.addMouseListener(this);
//
//        repaint();
//    }
//
//    // this method should be overriden
//    public void mouseClicked(MouseEvent e)
//    {
//    }
//
//    public void mousePressed(MouseEvent e)
//    {
//    }
//
//    public void mouseReleased(MouseEvent e)
//    {
//    }
//
//    public void mouseEntered(MouseEvent e)
//    {
//    }
//
//    public void mouseExited(MouseEvent e)
//    {
//    }
//}
//
//// accepts listeners - get x/y coordinates to determine what coordinate was clicked
