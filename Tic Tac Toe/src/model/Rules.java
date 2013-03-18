package model;
import java.util.Map;
import general.Location;

public class Rules {
	private Map<Location, Integer> updatedLocations;
	
	public Location getNextMoveLocation() {
		return null;
	}
	
	public int currentPlayer() {
		return -1;
	}
	
	public void makeMove(int player, Location loc) {
		
	}
	
	public Map<Location, Integer> getUpdatedLocations() {
		return updatedLocations;
	}
	
	public void clearUpdates() {
		
	}
}
