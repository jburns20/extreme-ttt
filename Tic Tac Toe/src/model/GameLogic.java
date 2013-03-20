package model;
import java.util.*;
import general.Location;

/**
 * The GameLogic class serves to process moves from the controller and to manage the state of the board model.
 */
public class GameLogic {
	private Map<Location, Integer> updatedLocations;
	private GameBoard mainBoard;
	
	/**
	 * Creates a new GameLogic instance with the specified number of levels and dimensions.
	 */
	public GameLogic(int levels, int dimensions) {
		
	}
	
	/**
	 * Returns a list of the locations that were updated as a result of previous moves.
	 */
	public Map<Location, Integer> getUpdatedLocations() {
		return updatedLocations;
	}
	
	/**
	 * Clears the list of the updated locations.
	 */
	public void clearUpdates() {
		updatedLocations = new HashMap<Location, Integer>();
	}
}
