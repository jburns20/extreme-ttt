package model;

import general.Location;

/**
 * An interface specifying the necessary behaviors of any computer
 * players (AIs).
 */ 
public interface AI {
	
	/**
	 * 
	 */ 
	Location selectMove(GameBoard board, Location playLocation);
	String wittyCatchphrase();
	int getDifficultyLevel();
}
