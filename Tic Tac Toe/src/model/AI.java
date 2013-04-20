package model;

import general.Location;

/**
 * An interface specifying the necessary behaviors of any computer
 * players (AIs).
 */ 
public interface AI {
	
	/**
	 * Determines location of the next move within the board and returns it.
	 * This is where the logic of the move takes place.
	 */ 
	Location getMove(GameBoard board, Location playLocation);
}