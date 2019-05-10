package Connect4;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Display extends JComponent implements MouseListener
{
    private Board board;

    private JFrame frame;
    
    private final Color BACKGROUND_COLOR = Color.white;
    private final Color GRID_COLOR = Color.gray;
    
    public Display(Player p1, Player p2)
    {
        this.board = new Board(p1, p2);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
        
    }
    
    private void createAndShowGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this); // very important line of code reee
        frame.setSize(541,441); // mults of 70
        frame.setLocation(200,100);
        frame.setTitle("Connect 4");
        frame.setResizable(false);
        Container c = frame.getContentPane();
        c.setBackground(BACKGROUND_COLOR);
        
        
        frame.setVisible(true);

        frame.addMouseListener(this);
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
        
        paintGrid(g);
        paintPieces(g);
    }
    
    private void paintGrid(Graphics g) {
        g.setColor(GRID_COLOR);
        for (int i=0;i<8;i++) {
            System.out.println(75*i);
            g.fillRect(75*i, 0, 10, 441);
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

    public void mousePressed(MouseEvent e) {}
 
     public void mouseReleased(MouseEvent e) {}
 
     public void mouseEntered(MouseEvent e) {}
 
     public void mouseExited(MouseEvent e) {}
}

// accepts listeners - get x/y coordinates to determine what coordinate was clicked