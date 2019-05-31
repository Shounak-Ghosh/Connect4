package Connect4;
import java.awt.*;

import java.util.*;

public abstract class GoPiece {
	private GoBoard board;
	private Location location;
	private Color color;
	private String imageFileName;
	private boolean hasMoved;

	/**
	 * Constructs piece
	 * @param col the color
	 * @param fileName the file name
	 * @param val the value of the piece
	 */
	public GoPiece(Color col, String fileName)
	{
		color = col;
		imageFileName = fileName;
	}

	/**
	 * This the piece has moved
	 * @return true if it has; otherwise false
	 */
	public boolean hasMoved()
	{
		return hasMoved;
	}

	/**
	 * Undo that it has moved
	 */
	public void undoMoved()
	{
		hasMoved = false;
	}

	/**
	 * Sets hte move status to true
	 */
	public void setMove()
	{
		hasMoved = true;
	}

	/**
	 * Checks if has moved
	 * @return true
	 */
	public boolean moveCounter()
	{
		return hasMoved();
	}

	/**
	 * Return the board that it is on
	 * @return the board
	 */
	public GoBoard getBoard()
	{
		return board;
	}

	/**r
	 * 
	 *returns the location of this piece on the board
	 *@return the location of the piece
	 */
	public Location getLocation()
	{
		return location;
	}

	/**
	 * 
	 *returns the color of this piece
	 *@return the color fo the piece
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * 
	 *returns the name of the file used to display this piece
	 *@return the filename
	 */
	public String getImageFileName()
	{
		return imageFileName;
	}


	/**
	 * Puts this piece into a board. If there is another piece at the given
	 * location, it is removed. <br />
	 * Precondition: (1) This piece is not contained in a grid (2)
	 * <code>loc</code> is valid in <code>gr</code>
	 * @param brd the board into which this piece should be placed
	 * @param loc the location into which the piece should be placed
	 */
	public void putSelfInGrid(GoBoard brd, Location loc)
	{
		GoPiece piece = brd.get(loc);
		if (piece != null)
			piece.removeSelfFromGrid();
		brd.put(loc, this);
		board = brd;
		location = loc;
	}

	/**
	 * Removes this piece from its board. <br />
	 * Precondition: This piece is contained in a board
	 */
	public void removeSelfFromGrid()
	{
		if (board == null)
			throw new IllegalStateException(
					"This piece is not contained in a board.");
		if (board.get(location) != this)
			throw new IllegalStateException(
					"The board contains a different piece at location "
							+ location + ".");

		board.remove(location);
		board = null;
		location = null;
	}

	/**
	 * Moves this piece to a new location. If there is another piece at the
	 * given location, it is removed. <br />
	 * Precondition: (1) This piece is contained in a grid (2)
	 * <code>newLocation</code> is valid in the grid of this piece
	 * @param newLocation the new location
	 */
	public void moveTo(Location newLocation)
	{
		if (board == null)
			throw new IllegalStateException("This piece is not on a board.");
		if (board.get(location) != this)
			throw new IllegalStateException(
					"The board contains a different piece at location "
							+ location + ".");
		if (!board.isValid(newLocation))
			throw new IllegalArgumentException("Location " + newLocation
					+ " is not valid.");

		if (newLocation.equals(location))
			return;
		board.remove(location);
		Piece other = board.get(newLocation);
		if (other != null)
			other.removeSelfFromGrid();
		location = newLocation;
		board.put(location, this);
	}

	/**
	 * This check if it is a valid destination
	 * @param dest the location
	 * @return true if it is; otherwise false
	 */
	public boolean isValidDestination(Location dest)
	{
		int nums = board.getNumRows() - dest.getRow();
		int num2 = board.getNumCols() - dest.getCol();
		if(dest.getRow() >= board.getNumRows() || dest.getCol() >= board.getNumCols() || 
				dest.getCol() < 0|| dest.getRow() < 0)
			return false;
		return board.get(dest) == null || !board.get(dest).getColor().equals(color);
	}

	/**
	 * This checks the valid destinations of the piece and returns it
	 * @return the valid locations the piece can go
	 */
	public abstract ArrayList<Location> destinations();

	/**
	 * Checks the following things in a certain direction
	 * @param dests the destinations
	 * @param direction the direction
	 * 
	 */
	public void sweep(ArrayList<Location> dests, int direction)
	{
		int row = location.getRow();
		int col = location.getCol();
		if(direction == 0 )
		{
			int i = 1;
			int k = 0;
			while(i > 0 && isValidDestination(new Location(row, col + i)))
			{
				k = i;
				if(board.get(new Location(row, col + i)) == null)
					dests.add(new Location(row, col + i));
				else
					i = -1;
				i++;
			}
			if(isValidDestination(new Location(row, col + k)))
				dests.add(new Location(row, col + k));
		}
		else if(direction == 90)
		{
			int i = -1;
			int k = 0;
			while(i < 0 && isValidDestination(new Location(row+i, col)))
			{
				k = i;
				if(board.get(new Location(row+i, col)) == null)
					dests.add(new Location(row+i, col));
				else
					i = 1;
				i--;
			}
			if(isValidDestination(new Location(row + k, col)))
				dests.add(new Location(row + k, col));
		}
		else if(direction == 180)
		{
			int i = 1;
			int k = i;
			while(i > 0 && isValidDestination(new Location(row, col-i)))
			{
				k = i;
				if(board.get(new Location(row, col-i)) == null)
					dests.add(new Location(row, col-i));
				else
					i = -1;
				i++;
			}
			if(isValidDestination(new Location(row, col - k)))
				dests.add(new Location(row, col - k));
		}
		else if(direction == 270)
		{
			int i = -1;
			int k = i;
			while(i < 0 && isValidDestination(new Location(row-i, col)))
			{
				k = i;
				if(board.get(new Location(row-i, col)) == null)
					dests.add(new Location(row-i, col));
				else
					i = 1;
				i--;
			}
			if(isValidDestination(new Location(row-k, col)))
				dests.add(new Location(row-k, col));
		}

	}

	/**
	 * this checks diagonally and sticks valid moves into list
	 * @param list the list
	 */
	public void diagonal(ArrayList<Location> list)
	{
		int i = 1;
		int row = getLocation().getRow();
		int col = getLocation().getCol();
		while(isValidDestination(new Location(row + i, col + i)) && 
				getBoard().get(new Location(row + i, col + i))==null)
		{
			list.add(new Location(row + i, col + i));
			i++;
		}
		if(isValidDestination(new Location(row + i, col + i)))
			list.add(new Location(row + i, col+i));

		i = -1;
		while(isValidDestination(new Location(row + i, col + i)) && 
				getBoard().get(new Location(row + i, col + i))==null)
		{
			list.add(new Location(row + i, col + i));
			i--;
		}
		if(isValidDestination(new Location(row + i, col + i)))
			list.add(new Location(row + i, col+i));

		i= 1;
		while(isValidDestination(new Location(row + i, col - i)) && 
				getBoard().get(new Location(row + i, col - i))==null)
		{
			list.add(new Location(row + i, col - i));
			i++;
		}
		if(isValidDestination(new Location(row + i, col - i)))
			list.add(new Location(row + i, col-i));

		i= -1;
		while(isValidDestination(new Location(row + i, col - i)) && 
				getBoard().get(new Location(row + i, col - i))==null)
		{
			list.add(new Location(row + i, col - i));
			i--;
		}
		if(isValidDestination(new Location(row + i, col - i)))
			list.add(new Location(row + i, col-i));
	}

	/**
	 * useless
	 * @param piece target piece
	 * @return if still a target
	 */
	public boolean stillTarget(Piece piece)
	{
		ArrayList<Location> list = piece.destinations();
		Iterator<Location> it = list.iterator();
		while(it.hasNext())
		{
			Location loc = it.next();
			if(loc.equals(location))
				return true;
		}
		return false;
	}

	/**
	 * The to string
	 * @return the string
	 */
	public String toString()
	{
		return imageFileName + " " + getLocation();
	}
}
