package view;
import general.Location;

/**
 * The GameViewDelegate interface specifies the functionality of any Controller to prevent View dependency
 * on written Controller classes. Informs the controller of any user input on the view.
 */
public interface GameViewDelegate {
	/**
	 * Informs the controller of a click "heard" by a MouseListener at the specified location
	 */
	public void locationClicked(Location loc);
	
	/**
	 * Informs the controller of the user's intention to reset the scores.
	 */
	public void resetClicked();
	
	/**
	 * Informs the controller of the user's intention to restart the game but keep the current score.
	 */
	public void restartClicked();
}
