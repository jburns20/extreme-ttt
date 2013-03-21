package view;
import general.Location;

public interface GameFrameDelegate {
	public void locationClicked(Location loc);
	public void resetClicked();
	public void restartClicked();
}
