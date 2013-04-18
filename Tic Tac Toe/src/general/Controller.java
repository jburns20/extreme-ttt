package general;
import view.GameFrame;
import view.GameViewDelegate;
import model.AI;
import model.GameLogic;

/**
 * The main controller that controls the game.
 */
public class Controller implements GameViewDelegate {
	private AI[] players;
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
		startNewMatch();
	}
	
	/**
	 * Starts a new match by creating a new game window.
	 */
	public void startNewMatch() {
		frame = new GameFrame(2, 3, new String[] {"Player 1", "Player 2"});
		frame.setDelegate(this);
		logic = new GameLogic(2, 3);
		frame.setValidLocation(logic.getValidMoveLocation());
	}

	/**
	 * Methods implementing the GameViewDelegate interface.
	 */
	public void locationClicked(Location loc) {
		logic.takeTurn(loc);
		System.out.println(logic.getUpdatedLocations());
		frame.updateLocations(logic.getUpdatedLocations());
		logic.clearUpdates();
		frame.setValidLocation(logic.getValidMoveLocation());
	}

	public void resetClicked() {
		System.out.println("Reset clicked!");
	}

	public void restartClicked() {
		System.out.println("Restart clicked!");
	}
}
