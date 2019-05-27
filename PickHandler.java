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
    
    public PickHandler() {
        displaySelf();
    }
    
    private Player player1;
    private Player player2;

    public void pick() {
//        while(player1==null || player2==null) {
//             
//        }
//        return;
        player1 = new HumanPlayer("player1",Color.RED);
        player2 = new HumanPlayer("player2",Color.YELLOW);
    }
    
    
    
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("hi 2");
//        int xCoord = e.getX();
//        int yCoord = e.getY();
//        System.out.println(xCoord+" "+yCoord);
//        
//        if (xCoord-insets.left>250 && xCoord-insets.left<500) {
//            if (Math.abs((yCoord-insets.top)-300)<25) { // PLAY button clicked
//                System.out.println("playing...");
//                removeSelf();
//                Game game = new Game();
//                games.add(game);
//            } else if (Math.abs((yCoord-insets.top)-375)<25) { // Archived Games button clicked
//                System.out.println("getting archived games...");
//                removeSelf();
//                GameListHandler gameListHandler = new GameListHandler();
//            }
//        }
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
        
        JTextField player1field = new JTextField(20);
        player1field.setBounds(315, 120, 150, 40);
        player1field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        this.add(player1field);
        
        JTextField player2field = new JTextField(20);
        player2field.setBounds(315, 270, 150, 40);
        player2field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        this.add(player2field);
        
        addKeyListeners(player1field, player2field);

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
