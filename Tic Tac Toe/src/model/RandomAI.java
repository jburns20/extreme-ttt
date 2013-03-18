package model;

import general.Location;

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
