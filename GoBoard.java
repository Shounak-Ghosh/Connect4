package Connect4;

import java.awt.Color;
import java.util.Stack;

//test growl
/**
 * Represents the game board A game board handles all player actions
 */
public class GoBoard
{
    private GoPiece[][] grid;
    // private int lastColumn; // the last column that a piece was placed in
    private Stack<int[]> moves; 

    /**
     * Creates a new Board
     * 
     * @param p1 player 1
     * @param p2 player 2
     */
    public GoBoard()
    {
        grid = new GoPiece[19][19];
        // lastColumn = -1;
        moves = new Stack<int[]>();
    }
    
    public Stack<int[]> getMoves() {
        return moves;
    }

    /**
     * @param column the tested column
     * @return if the column has at least 1 empty slot
     */
    public boolean isValidMove(int row, int column) 
    {
    
        return (column >= 0 && row>=0 && column<19 &&row <19 &&grid[row][column]==null);
    }

    /**
     * @return the grid of pieces
     */
    public GoPiece[][] getPieces()
    {
        return grid;
    }
    
    
    
    //VERY IMPORTANT - MOVES WILL BE MADE WITHIN EACH PLAYER CLASS AND HANDLED BY GOGAME
    
    

    
    /**
     * Moves a piece into the given column as far as it can go.
     * 
     * @precondition the column has at least 1 empty slot
     * @param column the given column
     */
    public void executeMove(int[] move, GoPlayer player)
    {
        
        GoPiece p = new GoPiece(player.getColor(), player);
        grid[row][column] = p;
        try
        {
            Thread.sleep(1000); // should be at 300
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
        
        
//        updateCurrentPlayer();

        // lastColumn = column; // resets last column
//        if (!moves.isEmpty())
//        {
//            int unhighlightRow = getTopmostEmptySlot(moves.peek()) + 1;
//            if (moves.peek() == column)
//                unhighlightRow++;
//
//            grid[unhighlightRow][moves.peek()].highlight(false);
//        }
//        moves.push(column);
//  }

//    public void makeTempMove(int row, int column, Color c)
//    {
//        
//        System.out.println("current player " + currentPlayer);
//        grid[row][column] = new GoPiece(c, currentPlayer);
//        
//        System.out.println(currentPlayer.printColor());
//        int[] move = {row,column};
//        moves.push(move);
//        System.out.println("tempMove " + moves);
//    }

    

    /**
     * Undos the last move on the board
     */
    public void undo()
    {
        if (moves.isEmpty())
            return;

        int[] move = moves.pop();
        grid[move[0]][move[1]] = null;

        //updateCurrentPlayer();
    }

    
    public void restart() 
    {
        moves = new Stack<int[]>();
        for (int i = 0; i < grid.length; i++) 
        {
            for (int j = 0; j < grid[i].length; j++) 
            {
                grid[i][j] = null;
            }
        }
        
        
        
    }
    
    /**
     * REWRITE THIS - IT'S WRITTEN SO BADLY oml
     * 
     * @return if the winning player on the current board setup. if there is no
     *         winner, returns null.
     */
    public Player winner()
    {

        Player player = null;

        if (moves.size() >= 361)
        {
            System.out.println("DRAW");
            return null;
        }

        // testing horizontal
        for (int r = 0; r < grid.length; r++)
        {
//            for (int c = 0; c < grid[0].length - 3; c++)
//            {
//                GoPiece test = grid[r][c];
//
//                if (test != null)
//                {
//                    // test horizontally
//                    if (test.is(grid[r][c + 1]) && test.is(grid[r][c + 2]) && test.is(grid[r][c + 3]))
//                    {
//
//                        test.highlight(true);
//                        grid[r][c + 1].highlight(true);
//                        grid[r][c + 2].highlight(true);
//                        grid[r][c + 3].highlight(true);
//
//                        player = test.getPlayer();
//                    }
//                }
//            }
        	for(int c = 0; c<grid.length-5; c++)
        	{
        		GoPiece p = grid[r][c];
        		
        		if(p!=null)
        		{
        			boolean won = true;
        			Color tempC = p.getColor();
        			for(int i = c; i<c+5; i++)
        			{
        				if(grid[r][i]==null|| !grid[r][i].getColor().equals(tempC))
        				{
        					won = false;
        				}
        			}
        			if(p!=null&&won)
        			{
        				player= p.getPlayer();
        			}
        			
        		}
        	}
        	//to be continued
        	
        }
        

        // testing vertical
        for (int c = 0; c < grid[0].length; c++)
        {
            for (int r = 0; r < grid.length-5; r++)
            {
            	GoPiece p = grid[r][c];
        		
        		if(p!=null)
        		{
        			boolean won = true;
        			Color tempC = p.getColor();
        			for(int i = r; i<r+5; i++)
        			{
        				if(grid[i][c]==null|| !grid[i][c].getColor().equals(tempC))
        				{
        					won = false;
        				}
        			}
        			if(p!=null&&won)
        			{
        				player= p.getPlayer();
        			}
        			
        		}
            }
        }

        // test diagonal with negative slope
        for (int r = 0; r < grid.length-5; r++)
        {
            for (int c = 0; c <grid[0].length-5; c++)
            {
                GoPiece p = grid[r][c];

                if(p!=null)
                {
                	Color color = p.getColor();
                	boolean win = true;
                	for(int i = r; i<r+5; i++)
                	{
                		for(int j = c; j<c+5; j++)
                		{
                			if(grid[i][j]==null || !grid[i][j].getColor().equals(color))
                			{
                				win = false;
                			}
                		}
                	}
                	if(win)
                	{
                		player = p.getPlayer();
                	}
                }

            }
        }

        // test diagonally with positive slope
        for(int r = 0; r<grid.length-5; r++)
        {
        	for(int c = 4; c<grid[0].length; c++)
        	{
        		GoPiece p = grid[r][c];
        		if(p!=null)
        		{
        			boolean won = true;
        			Color color = p.getColor();
        			for(int i = r; i<r+5; i++)
        			{
        				for(int j = c; j>c-5;j--)
        				{
        					if(grid[i][j]==null|| !grid[i][j].getColor().equals(color))
        					{
        						won = false;
        					}
        				}
        			}
        			if(won)
        			{
        				player = p.getPlayer();
        			}
        		}
        	}
        }
        
        

        System.out.println("winner is " + player);

        return player;
    }

}
