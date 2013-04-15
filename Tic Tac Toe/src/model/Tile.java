package model;

/**
 * This class represents the model of any single "square" in Tic Tac Toe. It could be one of the smallest squares,
 * or one of the larger squares (represented as GameBoard objects, a subclass of Tile).
 */
public class Tile {
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;
	public static final int CAT = -1;
	
	private int value;
	
	public Tile() {
		value = EMPTY;
	}
	
	public boolean getValue() {
		return value;
	}
	
	public void setValue(int newValue) {
		value = newValue;
	}
}
