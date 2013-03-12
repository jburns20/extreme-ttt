package model;

public class Tile {
	public static final int X = 1;
	public static final int Y = 2;
	public static final int EMPTY = 0;
	
	private int value;
	
	public Tile() {
		value = EMPTY;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int newValue) {
		value = newValue;
	}
}
