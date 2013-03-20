package model;

import general.Location;

/**
 * An AI class that makes moves randomly. See the AI interface for method comments.
 */
public class RandomAI implements AI {

	public Location getMove(GameBoard board, Location playLocation) {
		return null;
	}

	public String wittyCatchphrase() {
		return "Beating you was sheer luck...";
	}

	public int getDifficultyLevel() {
		return 1;
	}

}
