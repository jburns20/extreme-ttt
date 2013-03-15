package general;

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
}
