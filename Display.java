package Connect4;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public abstract class Display extends JComponent implements MouseListener
{
    protected Board board;

    protected JFrame frame;

    protected final Color BACKGROUND_COLOR = new Color(192, 192, 192);
    protected final Color GRID_COLOR = Color.BLUE;
    protected final Color HIGHLIGHT_COLOR = new Color(0, 0, 153);
    protected final Color TEXT_COLOR = Color.WHITE;

    // insets of the frame (platform-dependent)
    protected Insets insets;

    public Display()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
