package Connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTextField;

public class PickHandler extends Display
{
    
    private JTextField player1field;
    private JTextField player2field;
    
    public PickHandler() {
        displaySelf();
    }
    
    private Player player1;
    private Player player2;

    public void pick() {
        while((player1==null || player2==null) && frame.isActive()) {
             System.out.println("waiting");
        }
        System.out.println(player1);
        System.out.println(player2);
        return;
        
    }
    
    
    
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("hi 2");
        int x = e.getX();
        int y = e.getY();
        
        if (isSubmit(x,y)) {
            System.out.println("submitting...");
            submitPlayerInfo();
        } else {
//            Color color = testColor
        }
        
    }
    // todo: don't allow semicolons in player names
    
    private void submitPlayerInfo() {
        String player1Name = player1field.getText();
        if (player1Name.equals("")) player1Name = "Player 1";
        String player2Name = player2field.getText();
        if (player2Name.equals("")) player2Name = "Player 2";
        
        
        player1 = new HumanPlayer(player1Name,Color.RED);
        player2 = new HumanPlayer(player2Name,Color.YELLOW);
    }
    
    private boolean isSubmit(int x, int y) { // write this
        x-=insets.left;
        y-= insets.top;
        return (Math.abs(x-390)<=75 && Math.abs(y-450)<20);
    }

    public void paintComponent(Graphics g)
    {
        System.out.println("repainting");
        
        games = new ArrayList<Game>();

        paintNameFields(g);
        paintColorSelections(g);
    }
    
    private void paintNameFields(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        g.setColor(Color.BLACK);
        g.drawString("Player 1:", 200, 150);
        g.drawString("Player 2:", 200, 300);
        
         player1field = new JTextField(20);
        player1field.setBounds(315, 120, 150, 40);
        player1field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        this.add(player1field);
        
         player2field = new JTextField(20);
        player2field.setBounds(315, 270, 150, 40);
        player2field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        this.add(player2field);
        
        addKeyListeners(player1field, player2field); // limit amount of characters

        g.setColor(Color.GRAY);
        g.fillRect(315, 430, 150, 40);
        
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        g.setColor(Color.WHITE);
        g.drawString("Submit", 345, 460);
    }
    
    private void highlight(int row, int column) {
        // write later
    }
    
    /**
     * p1 choices: red, blue, green
     * p2 choices: yellow, orange, purple
     * @param g
     */
    private void paintColorSelections(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(320, 185, 40, 40);
        g.setColor(Color.BLUE);
        g.fillOval(380, 185, 40, 40);
        g.setColor(Color.GREEN);
        g.fillOval(440, 185, 40, 40);
        
        g.setColor(Color.YELLOW);
        g.fillOval(320, 340, 40, 40);
        g.setColor(Color.ORANGE);
        g.fillOval(380, 340, 40, 40);
        g.setColor(Color.MAGENTA);
        g.fillOval(440, 340, 40, 40);
    }
    
    private void addKeyListeners(JTextField field1, JTextField field2) {
        KeyListener keyListener1 = new KeyListener() {
            public void keyPressed(KeyEvent evt) {}

            public void keyReleased(KeyEvent evt) {}

            public void keyTyped(KeyEvent evt) {
                if ((field1.getText() + evt.getKeyChar()).length() > 13) {
                    evt.consume();
                }
            }
          };
          field1.addKeyListener(keyListener1);
          KeyListener keyListener2 = new KeyListener() {
              public void keyPressed(KeyEvent evt) {}

              public void keyReleased(KeyEvent evt) {}

              public void keyTyped(KeyEvent evt) {
                  if ((field2.getText() + evt.getKeyChar()).length() > 13) {
                      evt.consume();
                  }
              }
            };
            field2.addKeyListener(keyListener2);
    }
    
    
    
    
    
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }

}
