package model;
import general.Location;

public class GameBoard extends Tile {
	
	private Tile[] board;
	
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
	
	public int getValue(Location loc) {
		return getValueRecursively(loc)[0].getValue();
	}
	
	public Tile[] getValueRecursively(Location loc) {
		if (loc.numValues() == 1) {return new Tile[] {board[loc.get(0)]};}
		return ((GameBoard)board[loc.get(0)]).getValueRecursively(loc.sublocation(1));
	}
}
