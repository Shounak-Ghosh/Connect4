package Connect4;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class GameDisplay extends Display {
    
    public GameDisplay(Player p1, Player p2) {
        this.board = new Board(p1, p2);
    }

    public void mouseClicked(MouseEvent e) {
        int column = getColumn(e.getX());
        
        if (board.isValidMove(column)) {
            board.makeMove(column);
            repaint();
        }
    }
    
    public void paintComponent(Graphics g)
    {
        System.out.println("repainting");
        
        updateWidthAndHeight();
        
        paintGrid(g);
        paintPieces(g);
    }
    
    /**
     * Paints the entire screen the grid color first, then paints
     * each empty "square" icon on. Does this so that it is easy
     * to give the empty slots filleted edges.
     * @param g
     */
    private void paintGrid(Graphics g) {
        frame.getContentPane().setBackground(GRID_COLOR);
        
        g.setColor(BACKGROUND_COLOR);
        
//        vertical benchmarks:
        for (int c=0;c<=6;c++) {
            for (int r=0;r<=5;r++) {
                g.fillRect(c * 75 + (c+1) * 10, 
                        r * 75 + (r+1) * 10, 
                        75, 75);
            }
        }
    }
    
    /**
     * WRITE THIS
     * basically go through board.getPieces, and where there is a piece,
     * use g to draw a circle with the piece's color. 
     * figure out dimensions yourself. :DD
     * @param g
     */
    private void paintPieces(Graphics g) {
        
    }
    
    /**
     * WRITE THIS
     * @param xCoord
     * @return
     */
    private int getColumn(int xCoord) {
        return xCoord/80;
    }
    
    private void updateWidthAndHeight() {
        width = frame.getWidth() - insets.left - insets.right;
        height = frame.getHeight() - insets.top - insets.bottom;
    }
    
}