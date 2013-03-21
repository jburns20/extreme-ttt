package general;
import view.GameFrame;
import view.GameFrameDelegate;
import model.AI;

/**
 * The main controller that controls the game.
 */
public class Controller implements GameFrameDelegate {
	private AI[] players;
	
	public static void main(String[] args) {
		new Controller();
	}
	
	public Controller() {
		startNewMatch();
	}
	
	public void startNewMatch() {
		GameFrame gameFrame = new GameFrame(2, 3, new String[] {"Player 1", "Player 2"});
		gameFrame.setDelegate(this);
	}

	public void locationClicked(Location loc) {
		// TODO Auto-generated method stub
		
	}

	public void resetClicked() {
		System.out.println("Reset clicked!");
		
	}

	public void restartClicked() {
		System.out.println("Restart clicked!");
		
	}
}
