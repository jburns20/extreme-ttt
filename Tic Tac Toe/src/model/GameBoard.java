package model;
import general.Location;

/**
 * A GameBoard is a subclass of Tile that contains inner Tile instances. Since GameBoard
 * extends Tile, GameBoards can also contain inner GameBoards, which can contain more
 * GameBoards, which can contain more GameBoards, which can contain more GameBoards,
 * which can contain more GameBoards, which can contain more GameBoards, which can contain
 * more GameBoards, which can contain Tiles.
 */
public class GameBoard extends Tile {
	
	/**
	 * An array of length (dimension*dimension) that holds the inner Tile references.
	 */
	private Tile[] board;
	int dimensions;
	
	/**
	 * Constructs a GameBoard with the specified dimensions and number of levels.
	 * Generates inner Tiles (which could be GameBoard instances) according to the
	 * number of levels needed.
	 */
	public GameBoard(int level, int dimension) {
		board = new Tile[(int)Math.pow(dimension, 2)];
		dimensions = dimension;
		if (level==1) {
			for (int x=0; x<board.length; x++) {
				board[x] = new Tile();
			}
		} else {
			for (int x=0; x<board.length; x++) {
				board[x] = new GameBoard(level-1, dimension);
			}
		}
	}
	
	public void setValue(Location loc, int player) {
		Tile temp = board[loc.get(0)];
		if (temp instanceof GameBoard) {
			((GameBoard)temp).setValue(loc.sublocation(1),player);
			this.updateValue(player);
		} else {
			temp.setValue(player);
		}
	}
	
	/**
	 * Checks if there is a win in this board and updates this board's value accordingly.
	 * Returns if this board's value was changed.
	 */
	public boolean updateValue(int player) {
		for (int index = 0; index < dimensions*dimensions; index+=(dimensions+1)) {
			if (board[index].getValue() != player) {return false;}
		}
		for (int index = dimensions-1; index < dimensions*dimensions; index+=(dimensions-1)) {
			if (board[index].getValue() != player) {return false;}
		}
		for (int start = 0; start<dimensions; start++) {
			for (int val = start; val < dimensions*dimensions; val+=dimensions) {
				if (board[val].getValue() != player) {return false;}
			}
		}
		for (int start = 0; start<dimensions*dimensions; start+=dimensions) {
			for (int val = start; val < start+dimensions; val++) {
				if (board[val].getValue() != player) {return false;}
			}
		}
		return true;
	}
	
	/**
	 * Returns the value of the Tile in this GameBoard at the specified location.
	 */
	public int getValue(Location loc) {
		return getValueRecursively(loc)[0].getValue();
	}
	
	private Tile[] getValueRecursively(Location loc) {
		if (loc.numValues() == 1) {return new Tile[] {board[loc.get(0)]};}
		return ((GameBoard)board[loc.get(0)]).getValueRecursively(loc.sublocation(1));
	}
}
