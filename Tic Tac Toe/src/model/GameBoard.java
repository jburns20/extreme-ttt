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
	
	/**
	 * Constructs a GameBoard with the specified dimensions and number of levels.
	 * Generates inner Tiles (which could be GameBoard instances) according to the
	 * number of levels needed.
	 */
	public GameBoard(int level, int dimension) {
		board = new Tile[(int)Math.pow(dimension, 2)];
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
