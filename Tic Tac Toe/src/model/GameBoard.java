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
	
	public Tile getValue(Location loc) {
		Tile[] next = this.getTiles();
		for (int i=0; i<loc.numValues()-2; i++) {
			next = ((GameBoard)next[loc.get(i)]).getTiles();
		}
		return next[loc.get(loc.numValues()-1)];
	}
	
	public Tile[] getTiles() {
		return board;
	}
}
