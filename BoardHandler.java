package Connect4;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class BoardHandler extends Display
{

    Graphics g;
    private boolean gameIsOver;
    private boolean computerized; // true if the game is against the computer
                                  // false if it's between 2 humans
    private int lastColumn;

    public BoardHandler(Player p1, Player p2)
    {
        this.board = new Board(p1, p2);
        gameIsOver = false;

        // change this for digitalplayer
        computerized = (p1 instanceof RandomPlayer || p2 instanceof RandomPlayer);
        lastColumn = -1;
    }

//    public void activate()
//    {
//        System.out.println("Reached play");
//        int move = 0;
//        while (!gameIsOver)
//        {            
//            if (!board.isHumanTurn())
//            {
//                System.out.println("Reached random players turn");
//                move = player2.getMove();
//                System.out.println(move);
//                while(!board.isValidMove(move)) 
//                {
//                    move = player2.getMove();
//                    System.out.println(move);
//                }
//                board.makeMove(move);
//                repaint();
//                currentPlayer = player1;
//            }
//            
////            try
////            {
////                Thread.sleep(1000);
////            }
////            catch (InterruptedException e)
////            {
////                e.printStackTrace();
////            }
//        }
//    }

    /**
     * Handles an entire set of moves in the game DOCUMENT THIS @GLORIA (ME)
     */
    public void mouseClicked(MouseEvent e)
    {
        // Human player is yellow, computer is red
        if (!gameIsOver && board.isHumanTurn()) // game is not over, the click represents a move
        {
            int column = getColumn(e.getX());

            if (makeMove(column) && computerized && !gameIsOver) // it is the non-human players move
            {
//                rest();

                // SLEEP HERE - FIGURE THIS OUT FOR DIGITALPLAYER
                
                makeMove(defensivePlayerMove());
                //makeMove(randomPlayerMove());
                //board.undo();
            }

        }
        else
        {
            // someone has already won the game, no more moves can be made
            System.out.println("no more...");
        }

    }
    
    
   
    
    private int randomPlayerMove() 
    {
        int move = (int) (Math.random() * 7);
        while(!board.isValidMove(move)) 
        {
            move = (int) (Math.random() * 7);
        }
        return move;
    }
    
    
    private int defensivePlayerMove() 
    {
        for(int i = 0; i < 7; i++) 
        {
            if(makeTempMove(i)) 
            {
                if(board.winner() != null) 
                {
                    board.undo();
                    return i;
                }
                board.undo();
            }
        }
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
        }
    }

    
    private boolean makeTempMove(int column) 
    {
        if(board.isValidMove(column)) 
        {
            board.makeTempMove(column);
            repaint();
            return true;
        }
        return false;
    }
    
    private boolean makeMove(int column)
    {

        if (board.isValidMove(column))
        {
            board.makeMove(column);
            repaint();

            // tests for a winner
            Player winner = board.winner();
            if (winner != null)
            {
                System.out.println("WINNER!");
                gameIsOver = true;
            }

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

        g.setColor(Color.BLACK);

    }

    /**
     * Paints the entire screen the grid color first, then paints each empty
     * "square" icon on. Does this so that it is easy to give the empty slots
     * rounded corners.
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
