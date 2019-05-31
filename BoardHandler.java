package Connect4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.HashSet;

/**
 * A BoardHandler object is in charge of painting the UI based
 * on the status of Board and determining the move of each player.
 * How the move is determined depends on the type of the player - for
 * example, if the player is of type HumanPlayer, the computer waits for 
 * a mouse click to determine a move. If the player is of type DefensivePlayer,
 * the computer uses the respective defensive move method to
 * determine the move. It then hands everything off to Board - 
 * BoardHandler only paints itself based on Board, and Board handles
 * the rest of the game.
 * @author Gloria Zhu, Shounak Ghosh
 * @version May 31 2019
 *
 */
public class BoardHandler extends Display {
    // not sure what this is for, but I guess it's required
    // for extending a jcomponent
    private static final long serialVersionUID = 1L;
    
    Graphics g;
    private boolean gameIsOver;
    private boolean computerized; // true if the game is against the computer
                                  // false if it's between 2 humans
    Player p1;
    Player p2;
    Player humanPlayer;
    Player computerPlayer;
    
    protected Board board;

    
    // Constructors/Getter

    public BoardHandler(Player p1, Player p2)
    {
        Noise.stopMenuMusic();
        this.board = new Board(p1, p2);
        gameIsOver = false;
        this.p1 = p1;
        this.p2 = p2;
        
        computerized = (p2 instanceof RandomPlayer || p2 instanceof DefensivePlayer || p2 instanceof SmartPlayer);
        if (computerized) {
            humanPlayer = p1;
            computerPlayer = p2;
        }
        
        displaySelf();
    }
    
    public BoardHandler(Player p1, Player p2, Board board) {
        this.board = board;
        gameIsOver = (board.winner()!=null);
        this.p1 = p1;
        this.p2 = p2;

        // change this for digital player
        // note: p2 will always be the computer bc we give human first turn
        computerized = (p2 instanceof RandomPlayer || p2 instanceof DefensivePlayer || p2 instanceof SmartPlayer);

        if (computerized) {
            humanPlayer = p1;
            computerPlayer = p2;
        }
    }
    
    public Board getBoard() { return board; }

    
    // mouseClick
    
    public void mouseClicked(MouseEvent e)
    {
        System.out.println("board clicked");
        int xCoord = e.getX();
        int yCoord = e.getY();

        if (isUndo(xCoord - insets.left, yCoord - insets.top))
        {
            Noise.playButtonNoise();
            board.undo();
            repaint();
            return;
        }
        
        if (isBack(xCoord - insets.left, yCoord - insets.top)) {
            Noise.playButtonNoise();
            storeGames();
            closeSelf();
            mainMenu.displaySelf();
            return;
        }

        if (!gameIsOver && board.isHumanTurn()) // game is not over, the click represents a move
        {
            int column = getColumn(xCoord - insets.left);

            // it was a valid move, computer's turn (if it is a 1 player game)
            if (makeMove(column) && computerized && !gameIsOver) {
                // figure out how to sleep here

                if (p2 instanceof RandomPlayer) {
                    makeMove(randomPlayerMove());
                } else if (p2 instanceof DefensivePlayer) {
                    makeMove(defensivePlayerMove());
                } else if (p2 instanceof SmartPlayer) {
                    makeMove(smartPlayerMove());
                }
            }

        } else {
            // someone has already won the game, no more moves can be made
            System.out.println("Game has ended.");
            repaint();
        }

    }
    
    
    // mouseClick helpers

    private boolean isUndo(int xCoord, int yCoord) {
        System.out.println(xCoord + " " + yCoord);
        return (yCoord >= 400 && yCoord <= 440 && xCoord >= 630 && xCoord <= 715);
    }
    
    private boolean isBack(int xCoord, int yCoord) {
        return (xCoord>=630 && xCoord<=715 && yCoord>=460 && yCoord<=500);
    }
    
    private int getColumn(int x) {
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
    
    
    // Determining Moves

    private int randomPlayerMove() {
        int move = (int) (Math.random() * 7);
        while (!board.isValidMove(move))
        {
            move = (int) (Math.random() * 7);
        }
        return move;
    }

    private int defensivePlayerMove()
    {
        for (int i = 0; i < 7; i++) {
//            board.setCurrentPlayer(p2);
            if (makeTempMove(i, Color.RED)) { // computer makes a move
                for (int j = 0; j < 7; j++) {
//                    board.setCurrentPlayer(p1); // set player to human player
                    if (makeTempMove(j, Color.YELLOW)) { // human makes a move
                        if (board.winner() != null && board.winner().equals(p1)) { // if the human wins on this move
                            System.out.println("MOVE TO BLOCK");
                            board.undo();
                            board.undo();
//                            board.setCurrentPlayer(p2); // set player to computer
                            return j; // blocks the move from occurring
                        }
                    }
                    board.undo();
                }
            }
            board.undo();
        }
        return randomPlayerMove();
    }

    private int smartPlayerMove(){
        System.out.println("smart player move");
        // HashSet<Integer> validColumns = new HashSet<Integer>();
        HashSet<Integer> oneMoveWin = new HashSet<Integer>();
        HashSet<Integer> twoMoveWin = new HashSet<Integer>();

        for (int a = 0; a < 7; a++) { // computer plays
            board.makeTempMove(a, computerPlayer.getColor(), computerPlayer);
            if (board.winner() != null && board.winner().equals(computerPlayer)) { // win if a win is possible
                board.clearTempMoves();
                return a;
            }
            for (int b = 0; b < 7; b++) { // human plays
                board.makeTempMove(b, humanPlayer.getColor(), humanPlayer);
                if (board.winner() != null && board.winner().equals(humanPlayer)) {
                    oneMoveWin.add(b); // this strategy does not always work
                    // add case where this move helps humanPlayer win
                }
                for (int c = 0; c < 7; c++) { // computer plays
                    board.makeTempMove(c, computerPlayer.getColor(), computerPlayer);
                    for (int d = 0; d < 7; d++) { // human plays
                        board.makeTempMove(d, humanPlayer.getColor(), humanPlayer);
                        if (board.winner() != null && board.winner().equals(humanPlayer)) {
                            twoMoveWin.add(d);
                        }
                        board.clearTempMoves();
                    }
                    board.clearTempMoves();
                }
                board.clearTempMoves();
            }
            board.clearTempMoves();
        }
        board.clearTempMoves();

        // flush out the invalid moves

        for (Integer i : oneMoveWin)
            if (!board.isValidMove(i))
                oneMoveWin.remove(i);
        for (Integer i : twoMoveWin)
            if (!board.isValidMove(i))
                twoMoveWin.remove(i);
        
        // give priority to one move wins
        if (!oneMoveWin.isEmpty())
            for (Integer i : oneMoveWin)
                return i;
        if (!twoMoveWin.isEmpty())
            for (Integer i : twoMoveWin)
                return i;

        int move = board.getMoveStack().peek() - 1 + (int) (Math.random() * 3);

        while (!board.isValidMove(move))
            move = board.getMoveStack().peek() - 1 + (int) (Math.random() * 3);
        
        return move;

    }

    
    // Making Temporary/Permanent Moves
    
    private boolean makeTempMove(int column, Color c)
    {
        if (board.isValidMove(column)) {
            repaint();
            return true;
        }
        return false;
    }

    private boolean makeMove(int column)
    {

        if (board.isValidMove(column)) {
            try { Noise.playDropNoise(); }
            catch (Exception e) { e.printStackTrace(); }
            
            int finalRow = board.getTopmostEmptySlot(column);
            int row = 0;
            double dropTime = 100;
            while (row <= finalRow)  {
                gameIsOver = true;
                board.animateMove(column, row);
                paint(getGraphics());
                
                try { Thread.sleep((int) dropTime); }
                catch (Exception e) { e.printStackTrace(); }
                
                board.removeAnimatedMove(column, row);
                paint(getGraphics());
                row++;
                dropTime *= 0.9;
                gameIsOver = false;
            }
            
            Player winner = board.makeMove(column);
            paint(getGraphics());

            // tests for a winner
            if (winner != null)
            {
                System.out.println("WINNER!");
                gameIsOver = true;
            }

            return true;
        }
        return false;
    }

    
    // UI painting
    
    public void paintComponent(Graphics g) {
        paintGrid(g);
        paintPieces(g);
        paintSidebar(g);
    }

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
    }

    private void paintGrid(Graphics g)
    {
        frame.getContentPane().setBackground(GRID_COLOR);

        g.setColor(BACKGROUND_COLOR);

        // adding in the empty slots
        for (int c = 0; c <= 6; c++)
            for (int r = 0; r <= 5; r++)
                paintSlot(g, c, r, BACKGROUND_COLOR);
    }

    private void paintSlot(Graphics g, int c, int r, Color color)
    {
        g.setColor(color);
        g.fillRoundRect(c * 75 + (c + 1) * 10, r * 75 + (r + 1) * 10, 75, 75, 45, 45);
    }

    private void highlight(Graphics g, int c, int r, Color color) {
        g.setColor(color);
        g.drawRoundRect(c * 75 + (c + 1) * 10, r * 75 + (r + 1) * 10, 75, 75, 45, 45);
        g.drawRoundRect(c * 75 + (c + 1) * 10 + 1, r * 75 + (r + 1) * 10 + 1, 73, 73, 45, 45);
        g.drawRoundRect(c * 75 + (c + 1) * 10 + 2, r * 75 + (r + 1) * 10 + 2, 71, 71, 45, 45);
        g.drawRoundRect(c * 75 + (c + 1) * 10 + 3, r * 75 + (r + 1) * 10 + 3, 71, 71, 45, 45);
        
    }

    private void paintPieces(Graphics g) {
        Piece[][] piece = board.getPieces();

        for (int c = 0; c < piece[0].length; c++) {
            for (int r = 0; r < piece.length; r++) {
                if (piece[r][c] != null) {
                    paintSlot(g, c, r, piece[r][c].getColor());
                    if (piece[r][c].isHighlighted()) {
                        highlight(g, c, r, HIGHLIGHT_COLOR);
                    }
                }
            }
        }
    }

}