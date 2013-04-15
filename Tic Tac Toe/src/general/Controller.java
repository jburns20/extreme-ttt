package general;
import view.GameFrame;
import view.GameViewDelegate;
import model.AI;

/**
 * The main controller that controls the game.
 */
public class Controller implements GameViewDelegate {
	private AI[] players;
	
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
		GameFrame gameFrame = new GameFrame(2, 3, new String[] {"Player 1", "Player 2"});
		gameFrame.setDelegate(this);
	}

	/**
	 * Methods implementing the GameViewDelegate interface.
	 */
	public void locationClicked(Location loc) {
		
	}

	public void resetClicked() {
		System.out.println("Reset clicked!");
	}

	public void restartClicked() {
		System.out.println("Restart clicked!");
	}
}
