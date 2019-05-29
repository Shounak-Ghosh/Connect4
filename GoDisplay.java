package Connect4;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GoDisplay extends Display
{

    Graphics g;
    private boolean gameIsOver;
    private boolean computerized; // true if the game is against the computer
                                  // false if it's between 2 humans
    private GoBoard board;
    private JButton[][] grid;
    private JFrame frame;
    private Color[][] colors;
    Player p1;
    Player p2;
    

    public GoDisplay(Player p1, Player p2)
    {
        this.board = new GoBoard(p1, p2);
        gameIsOver = false;
        this.p1 = p1;
        this.p2 = p2;
        grid = new JButton[19][19];
        colors = new Color[19][19];

        // change this for digitalplayer
        // note: p2 will always be the computer bc we give human first turn
        computerized = !(p2 instanceof GoHumanPlayer);//make later
    }

    
    
    //break for now
    
   

    /**
     * 
     * Constructs a new display for displaying the given board
     * @param board the board
     */ 
    public BoardDisplay(Board board)
    {
        this.board = board;
        grid = new JButton[board.getNumRows()][board.getNumCols()];
        colors = new Color[board.getNumRows()][board.getNumCols()];

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
        frame.getContentPane().setLayout(new GridLayout(board.getNumRows(), board.getNumCols()));

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
        Location loc = new Location(row, col);

        if (selectedPiece == null)
        {
            //we have just selected a piece for the first time.
            selectedPiece = board.get(loc);
            clearColors();
            if (selectedPiece != null)
            {
                setColor(loc, Color.YELLOW);
                for(Location location: selectedPiece.destinations())
                {
                    setColor(location, Color.YELLOW);
                }
            }
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
            if (selectedPiece != null)
            {
                setColor(loc, Color.YELLOW);
                ArrayList<Location> lo = selectedPiece.destinations();
                Iterator<Location> it = lo.iterator();
                while(it.hasNext())
                {
                    setColor(it.next(), Color.YELLOW);
                }
            }
        }
        else
        {
            //we have selected a move
            selectedMove = new Move(selectedPiece, loc);
            setColor(loc, Color.YELLOW);
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
                Location loc = new Location(row, col);

                Piece piece = board.get(loc);

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
    public Move selectMove()
    {
        try
        {
            selectedPiece = null;
            selectedMove = null;
            while (selectedMove == null)
                Thread.sleep(1);
            Move move = selectedMove;
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

}