package model;
import general.Location;

public class GameBoard extends Tile {
	
	private Tile[] board;
	
	public GameBoard(int level) {
		board = new Tile[(int)Math.pow(Rules.DIMENSION, 2)];
		if (level==1) {
			for (int x=0; x<board.length; x++) {
				board[x] = new Tile();
			}
		} else {
			for (int x=0; x<board.length; x++) {
				board[x] = new GameBoard(level-1);
			}
		}
	}
	
	public int getValue(Location loc) {
		Tile next = this;
		for (int i=0; i<Rules.LEVELS; i++) {
			next = next.getSubtile(loc.get(i));
		}
	}
	
	public Tile getSubtile(int i) {
		return board[i];
	}
}
