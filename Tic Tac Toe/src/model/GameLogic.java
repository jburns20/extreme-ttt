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
	private Location validMoveLocation;
	
	/**
	 * Creates a new GameLogic instance with the specified number of levels and dimensions.
	 */
	public GameLogic(int l, int d) {
		levels = l;
		dimensions = d;
		mainBoard = new GameBoard(l,d);
		validMoveLocation = new Location(new int[] {4});
	}
	
	/**
	 * Makes a move on the board and updates the current player and valid player location. 
	 * Checks for any locations that were updated as a result of this turn. 
	 * Returns true when the entire game has finished, and updates the match score accordingly.
	 */
	public boolean takeTurn(Location loc) {
		//iteratively update the boards affected by the move and add resulting changed locations to updatedLocations.
		if (currentPlayer==0) currentPlayer=1;
		else currentPlayer=0;
		
		if (getMainBoardState() == Tile.X) scores[0]++;
		else if (getMainBoardState() == Tile.O) scores[1]++; 
		return (getMainBoardState() != Tile.EMPTY);
	}
	
	/**
	 * Returns the state of the entire game.
	 */
	public int getMainBoardState() {
		return mainBoard.getValue();
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
}
