package view;

import general.Location;

import java.awt.*;

import javax.swing.*;

public class EntireGridPanel extends JPanel {
	private static final long serialVersionUID = 1789400913514003324L;
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;
	public static final int CAT = 3;
	
	private int[][][] values; //Ragged 3D array to store values: level, row, column
	private int validLocationRow;
	private int validLocationCol;
	
	public EntireGridPanel(int levels, int dimensions) { //level 0 is "smallest"
		//Initialize the ragged 3D array
		values = new int[levels+1][][];
		for (int lvl=0; lvl<=levels; lvl++) {
			values[lvl] = new int[(int)Math.pow(dimensions, levels-lvl)][(int)Math.pow(dimensions, levels-lvl)];
		}
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
	}
	
	/**
	 * Updates the value of a given location.
	 */
	public void setValue(Location loc, int value) {
		int[] LRC = loc.toLRC(GameFrame.levels, GameFrame.dimensions);
		values[LRC[0]][LRC[1]][LRC[2]] = value;
		repaint();
	}
	
	/**
	 * Sets the valid move location to the given location.
	 */
	public void setValidLocation(Location loc) {
		int[] LRC = loc.toLRC(GameFrame.levels, GameFrame.dimensions);
		validLocationRow = LRC[1];
		validLocationCol = LRC[2];
	}
	
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D)gg;
		
		//Draw the values in the grid.
		
		for (int lvl = 0; lvl <= GameFrame.levels; lvl++) {  //For each level...
			//Find the total number of rows and columns of squares to draw
			int thisLevelDimensions = (int)Math.pow(GameFrame.dimensions, GameFrame.levels-lvl);
			//Find each square's width and height
			double realWidth = this.getWidth()/(double)thisLevelDimensions;
			double realHeight = this.getHeight()/(double)thisLevelDimensions;
			
			//double leftoverWidth = 0, leftoverHeight=0;
			for (int r=0; r<thisLevelDimensions; r++) for (int c=0; c<thisLevelDimensions; c++) {
				int pixelWidth = (int)realWidth;
				//Fancy stuff to compensate for dimensions that don't work out evenly ... turns out it wasn't necessary
				/*
				leftoverWidth += realWidth-pixelWidth;
				if (leftoverWidth > 1) {
					pixelWidth++;
					leftoverWidth--;
				}
				*/
				int pixelHeight = (int)realHeight;
				/*
				leftoverHeight += realHeight-pixelHeight;
				if (leftoverHeight > 1) {
					pixelHeight++;
					leftoverHeight--;
				}
				*/
				if (lvl != GameFrame.levels) { //Don't draw a gigantic X or O when the game is over
					//Draw an X or O (if necessary) in this square
					drawValue(g, (int)(realWidth*c), (int)(realHeight*r), pixelWidth, pixelHeight, values[lvl][r][c]);
				}
				if (lvl == 1) { //Draw overlays over all squares except the valid move location
					if (validLocationCol != c || validLocationRow != r) {
						g.setColor(new Color(0,0,0,30));
						g.fillRect((int)(realWidth*c), (int)(realHeight*r), pixelWidth, pixelHeight);
					}
				}
				//Draw a border around this square
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(3*lvl+2));
				g.drawRect((int)(realWidth*c)+1, (int)(realHeight*r)+1, pixelWidth-1, pixelHeight-1);
			}
		}
		
	}
	
	/**
	 * Draw an X or an O in the square given by its position and dimensions.
	 */
	private void drawValue(Graphics g, int x, int y, int width, int height, int value) {
		if (value == X) {
			//g.setColor(new Color(200,0,0,64));
			g.drawImage(new ImageIcon("x.png").getImage(), x, y, width, height, null);
		} else if (value == O) {
			//g.setColor(new Color(0, 200, 0, 64));
			g.drawImage(new ImageIcon("o.png").getImage(), x, y, width, height, null);
		} else if (value == CAT) {
			//g.setColor(new Color(0, 0, 200, 64));
		} else {
			return;
		}
		//g.fillOval(x, y, width, height);
	}
}
