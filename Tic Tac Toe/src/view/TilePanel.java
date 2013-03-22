package view;

import java.awt.Graphics;

import general.Location;
import javax.swing.JPanel;

/**
 * Represents any square of the game view. Can show an image of an X or an O if necessary.
 */
public class TilePanel extends JPanel {
	private static final long serialVersionUID = -1330024308079666309L;
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;
	
	private Location loc;

	public TilePanel(Location l) {
		setOpaque(false);
		loc = l;
	}
	
	/**
	 * Sets this TilePanel to the specified value, changing the image.
	 */
	public void setValue(int value) {
		
	}
	
	public void paintComponent(Graphics g) {
		
	}
	
	/**
	 * Returns the location of this tile in the grid.
	 */
	public Location getGridLocation() {
		return loc;
	}
}
