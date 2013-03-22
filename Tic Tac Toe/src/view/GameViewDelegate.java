package view;
import general.Location;

public interface GameViewDelegate {
	public void locationClicked(Location loc);
	public void resetClicked();
	public void restartClicked();
}
