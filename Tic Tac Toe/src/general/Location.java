package general;

import java.util.Arrays;

/**
 * References any location on the TicTacToe board, whether in the view or the model.
 */
public class Location {
	/**
	 * A list of coordinates x (0<=x<=9) referencing the position of each sublevel
	 * within the next outer level.
	 */
	int[] loc; 
	
	public Location(int[] locations) {
		loc = locations;
	}
	
	public int get(int level) {
		return loc[level];
	}
	public int numValues() {
		return loc.length;
	}
	
	/**
	 * Returns a Location referencing the same location as this object that starts 
	 * at the specified level and ends at the specified level.
	 */
	public Location sublocation(int start, int end) {
		return new Location(Arrays.copyOfRange(loc, start, end));
	}
	
	/**
	 * Returns a Location referencing the same location as this object that starts 
	 * at the specified level.
	 */
	public Location sublocation(int start) {
		return sublocation(start,loc.length);
	}
	
	/**
	 * Returns a hash code for this location.
	 */
	public int hashCode() {
		return loc.hashCode();
	}
}
