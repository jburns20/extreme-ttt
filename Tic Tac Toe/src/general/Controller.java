package general;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
		JFrame frame = new JFrame();
		int levels = (Integer)JOptionPane.showInputDialog(frame,
															"Choose the number of levels", 
															"Extreme Tic-Tac-Toe",
															JOptionPane.QUESTION_MESSAGE,
															null,
															new Integer[] {1,2,3},
															1);
		int numPossible;
		if (levels==1) numPossible = 38;
		else if (levels==2) numPossible = 6;
		else numPossible = 2;
		Integer[] possibleDimensions = new Integer[numPossible];
		for (int i=0; i<numPossible; i++) {
			possibleDimensions[i] = i+3;
		}
		int dimensions = (Integer)JOptionPane.showInputDialog(frame,
															"Choose the dimensions of each grid", 
															"Extreme Tic-Tac-Toe",
															JOptionPane.QUESTION_MESSAGE,
															null,
															possibleDimensions,
															3);
		int numHumans = (Integer)JOptionPane.showInputDialog(frame,
				"Choose the number human players", 
				"Extreme Tic-Tac-Toe",
				JOptionPane.QUESTION_MESSAGE,
				null,
				new Integer[] {0,1,2},
				2);
		players = new AI[2];
		for (int i=0; i<2; i++) {
			if (numHumans == 0) {
				players[i] = new RandomAI();
			} else {
				numHumans--;
			}
		}
		playerNames = new String[] {"Player 1", "Player 2"};
		startNewMatch(levels,dimensions);
	}
	
	/**
	 * Starts a new match by creating a new game window.
	 */
	public void startNewMatch(int levels, int dimensions) {
		frame = new GameFrame(levels, dimensions, new String[] {"Player 1", "Player 2"});
		frame.setDelegate(this);
		logic = new GameLogic(levels, dimensions);
		frame.setValidLocation(logic.getValidMoveLocation());
		/*if (players[0] != null) {
			while (logic.getMainBoardState() != 0) {
				locationClicked(players[0].getMove(logic.getMainBoard(), logic.getValidMoveLocation()));
			}
		}*/
	}

	/**
	 * Methods implementing the GameViewDelegate interface.
	 */
	public void locationClicked(Location loc) {
		logic.takeTurn(loc);
		frame.updateLocations(logic.getUpdatedLocations());
		logic.clearUpdates();
		frame.setValidLocation(logic.getValidMoveLocation());
		frame.updateScores(logic.getScore(0), logic.getScore(1));
		if (logic.getMainBoardState() > 0) {
			win();
		}
	}
	
	public void win() {
		frame.gameIsOver(logic.getMainBoardState());
		int winner = logic.getMainBoardState();
		String wintext;
		if (winner != 3) {
			wintext = playerNames[winner-1] + " wins!";
		} else {
			wintext = "It's a tie!";
		}
		wintext += " Would you like to continue?";
		JFrame frame = new JFrame();
		int result = JOptionPane.showConfirmDialog(frame, wintext, "Extreme Tic-Tac-Toe", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (result==1) {
			System.exit(0);
		} else {
			restartClicked();
		}
	}

	public void resetClicked() {
		logic.resetScores();
		frame.updateScores(logic.getScore(0), logic.getScore(1));
	}

	public void restartClicked() {
		logic.restart();
		frame.clearBoard();
		frame.setValidLocation(logic.getValidMoveLocation());
	}
}
