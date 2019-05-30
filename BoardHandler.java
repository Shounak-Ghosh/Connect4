package Connect4;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BoardHandler extends Display
{

    Graphics g;
    private boolean gameIsOver;
    private boolean computerized; // true if the game is against the computer
                                  // false if it's between 2 humans
    private int lastColumn;
    Player p1;
    Player p2;
    
    protected Board board;


    private Clip dropNoise;

    public BoardHandler(Player p1, Player p2)
    {
        System.out.println("Reached boardHandler Constructor");
        this.board = new Board(p1, p2);
        gameIsOver = false;
        this.p1 = p1;
        this.p2 = p2;

        // change this for digitalplayer
        // note: p2 will always be the computer bc we give human first turn
        computerized = (p2 instanceof RandomPlayer || p2 instanceof DefensivePlayer);
        lastColumn = -1;
        
        
        try
        {
            dropNoise = AudioSystem.getClip();
            dropNoise.open(AudioSystem.getAudioInputStream(new File("drop.wav")));
        }
        catch (LineUnavailableException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (UnsupportedAudioFileException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public Board getBoard()
    {
        return board;
    }

    /**
     * Handles an entire set of moves in the game DOCUMENT THIS
     */
    public void mouseClicked(MouseEvent e)
    {
        int xCoord = e.getX();
        int yCoord = e.getY();

        if (isUndo(xCoord - insets.left, yCoord - insets.top))
        {
            board.undo();
            repaint();
            return;
        }

        // Human player is yellow, computer is red
        if (!gameIsOver && board.isHumanTurn()) // game is not over, the click represents a move
        {
            int column = getColumn(xCoord - insets.left);

            if (makeMove(column) && computerized && !gameIsOver) // it is the non-human players move
            {
//                rest();

                // SLEEP HERE - FIGURE THIS OUT FOR DIGITALPLAYER

                if (p2 instanceof RandomPlayer)
                {
                    makeMove(randomPlayerMove());
                }
                else if (p2 instanceof DefensivePlayer)
                {
                    makeMove(defensivePlayerMove());
                }
                // makeMove(randomPlayerMove());
                // board.undo();
                
            }

        }
        else
        {
            // someone has already won the game, no more moves can be made
            System.out.println("Game has ended.");
            board.restart();
            gameIsOver = false;
            board.setCurrentPlayer(p1);
            repaint();
        }

    }

    private boolean isUndo(int xCoord, int yCoord)
    {
        System.out.println(xCoord + " " + yCoord);
        return (yCoord >= 400 && yCoord <= 440 && xCoord >= 630 && xCoord <= 715);
    }

    private int randomPlayerMove()
    {
        int move = (int) (Math.random() * 7);
        while (!board.isValidMove(move))
        {
            move = (int) (Math.random() * 7);
        }
        return move;
    }

    // TODO: make a twoInARow method in board (similar to winner) and use that
    // instead
    private int defensivePlayerMove()
    {
        System.out.println("----------------------------------");
        
        for (int i = 0; i < 7; i++)
        {
            board.setCurrentPlayer(p2);
            if (makeTempMove(i, Color.RED)) // computer makes a move
            {
                
                for (int j = 0; j < 7; j++)
                {
                    board.setCurrentPlayer(p1); // set player to human player
                    if (makeTempMove(j, Color.YELLOW)) // human makes a move
                    {
                        
                        if (board.winner() != null && board.winner().equals(p1)) // if the human wins on this move
                        {
                            System.out.println("MOVE TO BLOCK");
                            board.undo();
                            board.undo();
                            board.setCurrentPlayer(p2); // set player to computer
                            return j; // blocks the move from occurring
                        }
                    }
                    board.undo();
                }
            }
            board.undo();
        }

        board.setCurrentPlayer(p2);
        return randomPlayerMove();
    }

    private void rest()
    {
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
    }

    private boolean makeTempMove(int column, Color c)
    {
        if (board.isValidMove(column))
        {
            board.makeTempMove(column, c);
            repaint();
            return true;
        }
        return false;
    }

    private boolean makeMove(int column)
    {

        if (board.isValidMove(column))
        {
//            board.makeMove(column);
//            try
//            {
//                Thread.sleep(1000); // should be at 300 milliseconds
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//            
            
            int finalRow = board.getTopmostEmptySlot(column);
            int row = 0;
            double dropTime = 400;
            while (row <= finalRow) 
            {
                board.animateMove(column, row);
                paint(getGraphics());
                // note: I used paint because repaint would only be called if the mouse is clicked again
                //       instead of after every iteration of the while loop
                //       this is why the pieces seem to appear and then disappear
                System.out.println("should have repainted row " + row);
                try
                {
                    Thread.sleep((int) dropTime); // should be at 300 milliseconds
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                board.removeAnimatedMove(column, row);
                paint(getGraphics());
                row++;
                dropTime *= 0.9;
            }
            board.makeMove(column);
            paint(getGraphics());
            try
            {
                dropNoise = AudioSystem.getClip();
                dropNoise.open(AudioSystem.getAudioInputStream(new File("drop.wav")));
            }
            catch (LineUnavailableException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (UnsupportedAudioFileException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            

            // tests for a winner
            Player winner = board.winner();
            if (winner != null)
            {
                System.out.println("WINNER!");
                gameIsOver = true;
            }
            dropNoise.start();
            
            return true;
        }
        return false;
    }

    public void paintComponent(Graphics g)
    {
        System.out.println("repainting");

        paintGrid(g);
        paintPieces(g);
        paintSidebar(g);
    }

    // TODO: WRITE THIS
    // SHOULD HAVE: THE TWO PLAYERS - THEIR COLORS & NAMES. THE ONE WHOSE TURN IT IS
    // SHOULD BE HIGHLIGHTED. ALSO HAVE A MAIN MENU BUTTON FOR RETURNING TO MAIN
    // MENU
    private void paintSidebar(Graphics g)
    {
        g.setColor(board.getPlayer1().getColor());
        g.fillOval(640, 50, 70, 70);
        g.setColor(board.getPlayer2().getColor());
        g.fillOval(640, 175, 70, 70);

        g.setColor(TEXT_COLOR);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        g.drawString(p1.name, 630, 30);
        g.drawString(p2.name, 630, 155);

        g.drawRect(630, 400, 85, 40);

        g.drawRect(630, 460, 85, 40);

        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        g.drawString("UNDO", 640, 429);
        g.drawString("BACK", 643, 489);
        // TODO: add restart "button"

    }

    /**
     * Paints the entire screen the grid color first, then paints each empty
     * "square" icon on.
     * 
     * @param g
     */
    private void paintGrid(Graphics g)
    {
        frame.getContentPane().setBackground(GRID_COLOR);

        g.setColor(BACKGROUND_COLOR);

        // adding in the empty slots
        for (int c = 0; c <= 6; c++)
        {
            for (int r = 0; r <= 5; r++)
            {
                paintSlot(g, c, r, BACKGROUND_COLOR);
            }
        }
    }

    /**
     * Paints a single slot in a given color, with provided row AND COLUMN.
     * 
     * @param g     the graphics painter
     * @param c     the column of the slot to be painted
     * @param r     the row of the slot to be painted
     * @param color
     */
    private void paintSlot(Graphics g, int c, int r, Color color)
    {
        g.setColor(color);
        g.fillRoundRect(c * 75 + (c + 1) * 10, r * 75 + (r + 1) * 10, 75, 75, 45, 45);
    }

    private void highlight(Graphics g, int c, int r, Color color)
    {
        g.setColor(color);
        g.drawRoundRect(c * 75 + (c + 1) * 10, r * 75 + (r + 1) * 10, 75, 75, 45, 45);
        g.drawRoundRect(c * 75 + (c + 1) * 10 + 1, r * 75 + (r + 1) * 10 + 1, 73, 73, 45, 45);
        g.drawRoundRect(c * 75 + (c + 1) * 10 + 2, r * 75 + (r + 1) * 10 + 2, 71, 71, 45, 45);
        g.drawRoundRect(c * 75 + (c + 1) * 10 + 3, r * 75 + (r + 1) * 10 + 3, 71, 71, 45, 45);
        
    }

    /**
     * Goes through board.getPieces, and where there is a piece, use g to draw a
     * circle with the piece's color.
     * 
     * @param g the graphics painter
     */
    private void paintPieces(Graphics g)
    {
        Piece[][] piece = board.getPieces();

        for (int c = 0; c < piece[0].length; c++)
        {
            for (int r = 0; r < piece.length; r++)
            {
                if (piece[r][c] != null)
                {
                    paintSlot(g, c, r, piece[r][c].getColor());
                    if (piece[r][c].isHighlighted())
                    {
                        highlight(g, c, r, HIGHLIGHT_COLOR);
                    }
                }
            }
        }
    }

    /**
     * Returns the column of the game that the given x-coordinate would be in.
     * 
     * @param x the x-coordinate
     * @return the column
     */
    private int getColumn(int x)
    {
        if (x >= 10 && x <= 85)
            return 0;
        else if (x >= 95 && x <= 170)
            return 1;
        else if (x >= 180 && x <= 255)
            return 2;
        else if (x >= 265 && x <= 340)
            return 3;
        else if (x >= 350 && x <= 425)
            return 4;
        else if (x >= 435 && x <= 510)
            return 5;
        else if (x >= 520 && x <= 595)
            return 6;
        return -1;
    }

}
