package general;

import java.util.Arrays;

/**
 * References any location on the TicTacToe board, whether in the view or the model.
 */
public class Location {
	/**
	 * A list of coordinates x (0 <= x < dimension*dimension) referencing the position of each sublevel
	 * within the next outer level.
	 */
	int[] loc; 
	
	public Location(int[] locations) {
		loc = locations;
	}
	public Location() {
		loc = new int[0];
	}
	
	/**
	 * Constructs a new Location with the inner position added to the existing outer location.
	 */
	public Location(Location outerLoc, int innerPos) {
		int[] newLoc = new int[outerLoc.numValues()+1];
		for (int x = 0; x<outerLoc.numValues(); x++) {
			newLoc[x] = outerLoc.get(x);
		}
		newLoc[newLoc.length-1] = innerPos;
		loc = newLoc;
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
	public boolean equals(Location other) {
		if (this.loc.length != other.loc.length) {return false;}
		for (int index = 0; index < loc.length; index++) {
			if (other.loc[index] != loc[index]) {
				return false;
			}
		}
		return true;
	}
}
