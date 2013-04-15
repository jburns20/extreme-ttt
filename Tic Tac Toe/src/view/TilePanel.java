package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import general.Location;
import javax.swing.*;

/**
 * Represents any square of the game view. Can show an image of an X or an O if necessary.
 */
public class TilePanel extends JLayeredPane implements ComponentListener {
	private static final long serialVersionUID = -1330024308079666309L;
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;
	
	private Location loc;
	private JPanel lowerPanel;
	private JPanel overlayPanel;

	public TilePanel(Location l, int level, int dimensions, GameFrame listener) {
		this.setLayout(null);
		loc = l;
		setOpaque(false);
		overlayPanel = new TokenOverlayPanel(EMPTY);
		overlayPanel.setSize(this.getWidth(), this.getHeight());
		overlayPanel.setLocation(0, 0);
		this.add(overlayPanel, new Integer(2));
		this.addComponentListener(this);
		if (level > 0) {
			System.out.println("Adding lower panel. Width: " + this.getWidth());
			lowerPanel = new JPanel();
			lowerPanel.setOpaque(false);
			lowerPanel.setLayout(new GridLayout(dimensions, dimensions));
			lowerPanel.setSize(this.getWidth(), this.getHeight());
			lowerPanel.setLocation(0, 0);
			for (int i=0; i<dimensions*dimensions; i++) {
				TilePanel newpanel = new TilePanel(new Location(loc,i), level-1,dimensions,listener);
				lowerPanel.add(newpanel);
				newpanel.validate();
				lowerPanel.validate();
			}
			this.add(lowerPanel, new Integer(1));
		} else {
			this.addMouseListener(listener);
		}
		//this.revalidate();
	}

	/**
	 * Sets this TilePanel to the specified value, changing the image.
	 */
	public void setValue(int value) {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(0, 200, 0, 64));
		g.fillOval(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * Returns the location of this tile in the grid.
	 */
	public Location getGridLocation() {
		return loc;
	}

	@Override
	public void componentHidden(ComponentEvent arg0) { }

	@Override
	public void componentMoved(ComponentEvent arg0) { }

	@Override
	public void componentResized(ComponentEvent arg0) {
		if (lowerPanel != null) lowerPanel.setSize(getWidth(), getHeight());
		overlayPanel.setSize(getWidth(), getHeight());
	}

	@Override
	public void componentShown(ComponentEvent arg0) { }
}
