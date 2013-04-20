package model;

import java.util.ArrayList;

import general.Location;

/**
 * An AI class that makes moves randomly. See the AI interface for method comments.
 */
public class RandomAI implements AI {

	public Location getMove(GameBoard board, Location playLocation) {
		ArrayList<Location> possibleMoves = new ArrayList<Location>();
		for (int x = 0; x < board.dimensions*board.dimensions; x++) {
			Location loc = new Location(playLocation,x);
			if (board.getValue(loc) == Tile.EMPTY) {
				possibleMoves.add(loc);
			}
		}
		return possibleMoves.get((int)(Math.random()*possibleMoves.size()));
	}

}
