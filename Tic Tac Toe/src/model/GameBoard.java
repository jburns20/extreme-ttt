package model;
import java.util.HashMap;
import java.util.Map;

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
	
	public Map<Location, Integer> setValue(Location fullLocation, Location relLoc, int player) {
		if (relLoc.numValues()>0 && board[relLoc.get(0)] instanceof GameBoard) {
			Tile temp = board[relLoc.get(0)];
			Map<Location, Integer> map = ((GameBoard)temp).setValue(fullLocation, relLoc.sublocation(1),player);
			if (this.updateValue(player)) {
				map.put(fullLocation.sublocation(0,fullLocation.numValues()-relLoc.numValues()), player);
			}
			return map;
		} else {
			Tile temp;
			if (relLoc.numValues() > 0) {
				temp = board[relLoc.get(0)];
			} else {
				temp = this;
			}
			temp.setValue(player);
			Map<Location, Integer> map = new HashMap<Location, Integer>();
			map.put(fullLocation, player);
			if (this.updateValue(player)) {
				map.put(fullLocation.sublocation(0,fullLocation.numValues()-1), player);
				this.setValue(player);
			}
			return map;
		}
	}
	
	/**
	 * Checks if there is a win in this board and updates this board's value accordingly.
	 * Returns if this board's value was changed.
	 */
	public boolean updateValue(int player) {
		if (this.getValue() != Tile.EMPTY) {return false;}
		boolean win = true; // must assume that a win took place, each for loop is designed to see if a win was NOT achieved
		for (int index = 0; index < dimensions*dimensions; index+=(dimensions+1)) {
			if (board[index].getValue() != player) {win = false;}
		}
		if (win) {return win;}
		win = true; 
		for (int index = dimensions-1; index < dimensions*dimensions; index+=(dimensions-1)) {
			if (board[index].getValue() != player) {win = false;}
		}
		if (win) {return win;}
		for (int start = 0; start<dimensions; start++) {
			win = true;
			for (int val = start; val < dimensions*dimensions; val+=dimensions) {
				if (board[val].getValue() != player) {win = false;}
			}
			if (win) {return win;}
		}
		for (int start = 0; start<dimensions*dimensions; start+=dimensions) {
			win = true;
			for (int val = start; val < start+dimensions; val++) {
				if (board[val].getValue() != player) {win = false;}
			}
			if (win) {return win;}
		}
		return win;
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
