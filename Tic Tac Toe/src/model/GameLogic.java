package model;
import java.util.*;
import general.Location;

/**
 * The GameLogic class serves to process moves from the controller and to manage the state of the board model.
 */
public class GameLogic {
	private Map<Location, Integer> updatedLocations;
	private GameBoard mainBoard;
	private int currentPlayer;
	private int levels;
	private int dimensions;
	private int[] scores;
	private Location validMoveLocation; //sub-board that can be moved in
	
	/**
	 * Creates a new GameLogic instance with the specified number of levels and dimensions.
	 */
	public GameLogic(int l, int d) {
		levels = l;
		dimensions = d;
		currentPlayer = 1;
		mainBoard = new GameBoard(l,d);
		int[] locArray = new int[levels-1];
		int index = (int)((dimensions+1)*((int)Math.pow(dimensions, levels-1)/2));
		for (int x = 0; x < locArray.length; x++) {
			System.out.println(x+", "+index);
			locArray[x] = index;
		}
		validMoveLocation = new Location(locArray);
		updatedLocations = new HashMap<Location, Integer>();
		scores = new int[2];
	}
	
	public boolean isValidMove(Location loc) {
		return validMoveLocation.equals(loc.sublocation(0,loc.numValues()-1)) && mainBoard.getValue(loc) == Tile.EMPTY;
	}
	
	public boolean specialCaseWin() {
		for (int x = 0; x < 9; x++) {
			if (mainBoard.getValue(new Location(validMoveLocation, x)) == Tile.EMPTY) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Makes a move on the board and updates the current player and valid player location. 
	 * Checks for any locations that were updated as a result of this turn. 
	 * Returns true when the is valid and has been made.
	 */
	public boolean takeTurn(Location loc) {
		if (isValidMove(loc)) {
			updatedLocations.putAll(mainBoard.setValue(loc,loc,currentPlayer));
			this.setValidMoveLocation(loc.sublocation(1));
		
			if (specialCaseWin()) {
				mainBoard.setValue(currentPlayer);
				updatedLocations.put(new Location(new int[]{}), currentPlayer);
			}
			
			if (currentPlayer==1) currentPlayer=2;
			else currentPlayer=1;
			
			if (getMainBoardState() != Tile.EMPTY) {
				if (getMainBoardState() == Tile.X) scores[0]++;
				else if (getMainBoardState() == Tile.O) scores[1]++;
			}
		
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the state of the entire game.
	 */
	public int getMainBoardState() {
		return mainBoard.getValue();
	}
	
	public Location getValidMoveLocation() {
		return validMoveLocation;
	}
	
	/**
	 * Returns the specified player's current score.
	 */
	public int getScore(int player) {
		if (player > 1 || player < 0) throw new IllegalArgumentException();
		return scores[player];
	}
	
	/**
	 * Restarts the game, clearing all tiles.
	 */
	public void restart() {
		mainBoard = new GameBoard(levels, dimensions);
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
	
	public void setValidMoveLocation(Location loc) {
		validMoveLocation = loc;
	}
}
