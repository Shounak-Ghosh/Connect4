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
    
    private JTextField player1field = new JTextField(20);
    private JTextField player2field = new JTextField(20);
    private JTextField difficultyfield = new JTextField(1);
    private Game game;
    int player1color;
    int player2color;
    
    public PickHandler(Game game) {
        this.game = game;
        System.out.println("hi pick");
        displaySelf();
        System.out.println("finished displaying pick");
        repaint();
        player1color = 1;
        player2color = 1;
//        pick();
    }
    
    private Player player1;
    private Player player2;
    
    public void mouseClicked(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        
        if (isSubmit(x,y)) {
            submitPlayerInfo();
            closeSelf();
            game.play();
        } else {
            selectColor(x,y);
            repaint();
        }
        
    }
    
    /**
     * Selects a color for the players based in given x/y coordinates of a click.
     * If the click is not in any color, does nothing.
     * I am aware there is probably a better way to do this. However, :(
     * @param x
     * @param y
     */
    private void selectColor(int x, int y) {
        x-=insets.left;
        y-=insets.top;
        
        int selected = 0;
        if (x>=325 && x<=355) {
            selected = 1;
        } else if (x>=385 && x<=415) {
            selected = 2;
        } else if (x>=445 && x<=475) {
            selected = 3;
        }
        if (selected==0) return;
        
        System.out.println(selected);
        
        // row 1
        if (y>=195 && y<=215) {
            player1color = selected;
        } else if (y>=350 && y<=370) { // row 2
            player2color = selected;
        }
        
    }
    
    // todo: don't allow semicolons in player names
    private void submitPlayerInfo() {
        String player1Name = player1field.getText();
        if (player1Name.equals("")) player1Name = "Player 1";
        String player2Name = player2field.getText();
        if (player2Name.equals("")) player2Name = "Player 2";
        
        Color player1Color = getPlayer1Color();
        Color player2Color = getPlayer2Color();
        
        player1 = new HumanPlayer(player1Name,player1Color);
        
        String difficulty = difficultyfield.getText();
        
        if (difficulty.equals("1")) {
            player2 = new RandomPlayer(player2Name,player2Color);
        } else if (difficulty.equals("2")) {
            player2 = new DefensivePlayer(player2Name,player2Color);
        } else if (difficulty.equals("3")) {
//            player2 = new SmartPlayer(player2Name,player2Color);
        } else {
            player2 = new HumanPlayer(player2Name,player2Color);
        }
    }
    
    private Color getPlayer1Color() {
        if (player1color==2) return Color.blue;
        else if (player1color==3) return Color.green;
        return Color.red;
    }
    
    private Color getPlayer2Color() {
        if (player2color==2) return Color.orange;
        else if (player2color==3) return Color.magenta;
        return Color.yellow;
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
        
        player1field.setBounds(315, 120, 150, 40);
        player1field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        this.add(player1field);
        
        player2field.setBounds(315, 270, 150, 40);
        player2field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        this.add(player2field);
        
        difficultyfield.setBounds(600,270,40,40);
        difficultyfield.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,22));
        this.add(difficultyfield);
        
        addKeyListeners(player1field, player2field); // limit amount of characters

        g.setColor(Color.GRAY);
        g.fillRect(315, 430, 150, 40);
        
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        g.setColor(Color.WHITE);
        g.drawString("Submit", 345, 460);
    }
    
    private void highlight(int row, int column, Graphics g) {
        g.setColor(Color.BLACK);
        
        int y;
        if (row==1) y = 185;
        else y = 340;
        
        int x;
        if (column==1) x = 320;
        else if (column==2) x = 380;
        else x = 440;
        
        g.drawOval(x, y, 40, 40);
        g.drawOval(x-1,y-1,42,42);
        g.drawOval(x+1, y+1, 38, 38);
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
        
        highlight(1,player1color,g);
        highlight(2,player2color,g);
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
