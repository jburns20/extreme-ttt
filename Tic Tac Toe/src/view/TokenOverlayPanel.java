package view;

import java.awt.Graphics;

import javax.swing.JPanel;

public class TokenOverlayPanel extends JPanel {
	private static final long serialVersionUID = 5684014560934866296L;
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;

	private int value;
	
	public TokenOverlayPanel(int newval) {
		value = newval;
		this.setOpaque(false);
	}
	
	public void setValue(int newval) {
		value = newval;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (value==X) {
			//Draw an X
		} else if (value==O) {
			//Draw an O
		}
	}
}
