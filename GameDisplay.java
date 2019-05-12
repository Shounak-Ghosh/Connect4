package Connect4;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class GameDisplay extends Display {
    
    Graphics g;
    private boolean gameIsOver;
    
    public GameDisplay(Player p1, Player p2) {
        this.board = new Board(p1, p2);
        gameIsOver = false;
    }
    
    public void play() {
        
    }

    public void mouseClicked(MouseEvent e) {
        if (!gameIsOver) { 
            // game is not over, the click represents a move
                                    
            int column = getColumn(e.getX());
            
            // if the clicked column can be moved in
            if (board.isValidMove(column)) {
                
                board.makeMove(column); // makes the move
                repaint(); // repaints the window to reflect the move
                
                // tests for a winner - if there is, ends the game
                // mouse clicks will have different functions
                Player winner = board.winner();
                if (winner!=null) {
                    System.out.println("WINNER!");
                    gameIsOver = true;
                }
                
                
            }
        } else {
            System.out.println("no more...");
        }
        
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
    // SHOULD BE HIGHLIGHTED. ALSO HAVE A MAIN MENU BUTTON FOR RETURNING TO MAIN MENU
    private void paintSidebar(Graphics g) {
        
    }
    
    /**
     * Paints the entire screen the grid color first, then paints
     * each empty "square" icon on. Does this so that it is easy
     * to give the empty slots rounded corners.
     * @param g
     */
    private void paintGrid(Graphics g) {
        frame.getContentPane().setBackground(GRID_COLOR);
        
        g.setColor(BACKGROUND_COLOR);
        
        // adding in the empty slots
        for (int c=0;c<=6;c++) {
            for (int r=0;r<=5;r++) {
                paintSlot(g,c,r,BACKGROUND_COLOR);
            }
        }
    }
        
    /**
     * Paints a single slot in a given color, with provided row AND COLUMN.
     * @param g the graphics painter
     * @param c the column of the slot to be painted
     * @param r the row of the slot to be painted
     * @param color
     */
    private void paintSlot(Graphics g, int c, int r, Color color) {
        g.setColor(color);
        g.fillRoundRect(c * 75 + (c+1) * 10, 
                r * 75 + (r+1) * 10, 
                75, 75, 45, 45);
    }
    
    /**
     * Goes through board.getPieces, and where there is a piece,
     * use g to draw a circle with the piece's color. 
     * @param g the graphics painter
     */
    private void paintPieces(Graphics g) {
        Piece[][] piece = board.getPieces();
        
        for(int c=0;c<piece[0].length;c++) {
            for(int r=0;r<piece.length;r++) {
                if (piece[r][c]!=null) {
                    paintSlot(g,c,r,piece[r][c].getColor());
                }
            }
        }
    }
    
    /**
     * Returns the column of the game that the given x-coordinate
     * would be in.
     * @param x the x-coordinate 
     * @return the column
     */
    private int getColumn(int x) {
        if (x>=10 && x<=85) return 0;
        else if (x>=95 && x<=170) return 1;
        else if (x>=180 && x<=255) return 2;
        else if (x>=265 && x<=340) return 3;
        else if (x>=350 && x<=425) return 4;
        else if (x>=435 && x<=510) return 5;
        else if (x>=520 && x<=595) return 6;
        return -1;
    }
    
}