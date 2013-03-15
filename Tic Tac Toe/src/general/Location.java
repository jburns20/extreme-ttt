package general;

import java.util.Arrays;

public class Location {
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
	
	public Location sublocation(int start) {
		return new Location(Arrays.copyOfRange(loc, start, loc.length));
	}
}
