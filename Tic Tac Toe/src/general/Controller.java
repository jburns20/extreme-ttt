package general;
import java.awt.event.*;

import javax.swing.*;

import view.GameFrame;
import view.GameViewDelegate;
import model.AI;
import model.GameLogic;
import model.RandomAI;

/**
 * The main controller that controls the game.
 */
public class Controller implements GameViewDelegate {
	private AI[] players;
	private String[] playerNames;
	private GameLogic logic;
	private GameFrame frame;
	private boolean humanMovesAllowed;
	
	/**
	 * The main method of the program.
	 */
	public static void main(String[] args) {
		new Controller();
	}
	
	/**
	 * Constructs a new controller object that makes a new game.
	 */
	public Controller() {
		JFrame frame = new JFrame(); //dummy object for passing into input dialogs for some reason
		//Ask user for number of levels
		Integer levels = (Integer)JOptionPane.showInputDialog(frame,
				"Choose the number of levels", 
				"Extreme Tic-Tac-Toe",
				JOptionPane.QUESTION_MESSAGE,
				null,
				new Integer[] {1,2,3},
				1);
		if (levels == null) System.exit(0);
		
		//Compute possible dimensions based on number of levels chosen
		int numPossible;
		if (levels==1) numPossible = 38;
		else if (levels==2) numPossible = 6;
		else numPossible = 2;
		Integer[] possibleDimensions = new Integer[numPossible];
		for (int i=0; i<numPossible; i++) {
			possibleDimensions[i] = i+3;
		}
		
		//Ask user for grid dimensions
		Integer dimensions = (Integer)JOptionPane.showInputDialog(frame,
				"Choose the dimensions of each grid", 
				"Extreme Tic-Tac-Toe",
				JOptionPane.QUESTION_MESSAGE,
				null,
				possibleDimensions,
				3);
		if (dimensions == null) System.exit(0);
		
		//Ask user for number of human players
		Integer numHumans = (Integer)JOptionPane.showInputDialog(frame,
				"Choose the number human players", 
				"Extreme Tic-Tac-Toe",
				JOptionPane.QUESTION_MESSAGE,
				null,
				new Integer[] {0,1,2},
				2);
		if (numHumans == null) System.exit(0);
		
		//Initialize the AI (if any)
		players = new AI[2];
		for (int i=0; i<2; i++) {
			if (numHumans == 0) {
				players[i] = new RandomAI();
			} else {
				numHumans--;
			}
		}
		playerNames = new String[] {"Player 1", "Player 2"};
		humanMovesAllowed = true;
		
		//Start the match
		startNewMatch(levels,dimensions);
	}
	
	/**
	 * Starts a new match by creating a new game frame and logic.
	 */
	public void startNewMatch(int levels, int dimensions) {
		frame = new GameFrame(levels, dimensions, new String[] {"Player 1", "Player 2"});
		frame.setDelegate(this);
		logic = new GameLogic(levels, dimensions);
		frame.setValidLocation(logic.getValidMoveLocation());
		
		//If AI vs. AI, pit them against each other with timers
		if (players[0] != null) {
			humanMovesAllowed = false;
			startAITimers();
		}
	}
	
	/**
	 * Creates and starts two repeating timers, one for each AI, in an AI vs. AI game.
	 */
	private void startAITimers() {
		Timer player0Timer = new Timer(2000, new ActionListener() {  
			//Fancy anonymous inner classes for listeners (copying Android design pattern)
			public void actionPerformed(ActionEvent evt) {
				if (logic.getMainBoardState() != 0 || makeAIMove(players[0])) {
					((Timer)evt.getSource()).stop();
				}
			}
		});
		player0Timer.setInitialDelay(1000);
		Timer player1Timer = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (logic.getMainBoardState() != 0 || makeAIMove(players[1])) {
					((Timer)evt.getSource()).stop();
				}
			}
		});
		player1Timer.setInitialDelay(2000);
		player0Timer.start();
		player1Timer.start();
	}
	
	/**
	 * Announces that there was a winner, asking the user if they want to play again.
	 */
	private void win() {
		humanMovesAllowed = false;
		int winner = logic.getMainBoardState();
		System.out.println();
		String wintext;
		if (winner != 3) {
			wintext = playerNames[winner-1] + " wins!";
		} else {
			wintext = "It's a tie!";
		}
		wintext += "\nWould you like to play again?";
		JFrame frame = new JFrame();
		int result = JOptionPane.showConfirmDialog(frame, wintext, "Extreme Tic-Tac-Toe", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (result==1) {
			System.exit(0);
		} else {
			restartClicked();
		}
	}
	
	/**
	 * Makes a move with the given AI. Returns whether the move caused a win or not.
	 */
	private boolean makeAIMove(AI ai) {
		Location loc = ai.getMove(logic.getMainBoard(), logic.getValidMoveLocation());
		logic.takeTurn(loc);
		frame.updateLocations(logic.getUpdatedLocations());
		logic.clearUpdates();
		frame.setValidLocation(logic.getValidMoveLocation());
		frame.updateScores(logic.getScore(0), logic.getScore(1));
		if (logic.getMainBoardState() > 0) {
			win();
			return true;
		}
		return false;
	}
	
	/**
	 * Makes a move at the given location (from a human).
	 */
	private void makeHumanMove(Location loc) {
		if (logic.takeTurn(loc)) {
			frame.updateLocations(logic.getUpdatedLocations());
			logic.clearUpdates();
			frame.setValidLocation(logic.getValidMoveLocation());
			frame.updateScores(logic.getScore(0), logic.getScore(1));
			if (logic.getMainBoardState() > 0) {
				win();
			} else if (players[1] != null) {
				//If a human vs. AI game, set a timer for the AI to move one second from now (while preventing the user from moving until that happens)
				humanMovesAllowed = false;
				new Timer(1000, new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						makeAIMove(players[1]);
						humanMovesAllowed = true;
						((Timer)evt.getSource()).stop();
					}
				}).start();
			}
		}
	}

	/**
	 * Methods implementing the GameViewDelegate interface.
	 */
	public void locationClicked(Location loc) {
		if (humanMovesAllowed) {
			makeHumanMove(loc);
		}
	}
	
	public void resetClicked() {
		logic.resetScores();
		frame.updateScores(logic.getScore(0), logic.getScore(1));
	}

	public void restartClicked() {
		humanMovesAllowed = true;
		logic.restart();
		frame.clearBoard();
		frame.setValidLocation(logic.getValidMoveLocation());
		if (players[0] != null) {
			humanMovesAllowed = false;
			startAITimers();
		}
	}
}
