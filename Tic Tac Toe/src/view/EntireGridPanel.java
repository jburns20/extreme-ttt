package view;

import general.Location;

import java.awt.*;
import javax.swing.*;

public class EntireGridPanel extends JPanel {
	private static final long serialVersionUID = 1789400913514003324L;
	public static final int X = 1;
	public static final int O = 2;
	public static final int EMPTY = 0;
	public static final int CAT = -1;
	
	private int[][][] values; //level, row, column
	private int validLocationRow;
	private int validLocationCol;
	
	public EntireGridPanel(int levels, int dimensions) { //level 0 is smallest
		values = new int[levels+1][][];
		for (int lvl=0; lvl<=levels; lvl++) {
			values[lvl] = new int[(int)Math.pow(dimensions, levels-lvl)][(int)Math.pow(dimensions, levels-lvl)];
		}
	}
	
	public void setValue(Location loc, int value) {
		int[] LRC = loc.toLRC(GameFrame.levels, GameFrame.dimensions);
		values[LRC[0]][LRC[1]][LRC[2]] = value;
		System.out.println("valid location: " + LRC[0] + ", " + LRC[1] + ", " + LRC[2]);
		System.out.println(value);
		repaint();
	}
	
	public void setValidLocation(Location loc) {
		int[] LRC = loc.toLRC(GameFrame.levels, GameFrame.dimensions);
		validLocationRow = LRC[1];
		validLocationCol = LRC[2];
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//draw the valid location
		double width = (double)this.getWidth();
		double height = (double)this.getHeight();
		for (int i=0; i<GameFrame.dimensions-1; i++) {
			width /= GameFrame.dimensions;
			height /= GameFrame.dimensions;
		}
		int x = (int)(width*validLocationCol);
		int y = (int)(height*validLocationRow);
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, (int)width, (int)height);
		
		//draw the values in the grid
		for (int lvl = 0; lvl <= GameFrame.levels; lvl++) {
			int thisLevelDimensions = (int)Math.pow(GameFrame.dimensions, GameFrame.levels-lvl);
			double realWidth = this.getWidth()/(double)thisLevelDimensions;
			double realHeight = this.getHeight()/(double)thisLevelDimensions;
			double leftoverWidth = 0, leftoverHeight=0;
			for (int r=0; r<thisLevelDimensions; r++) for (int c=0; c<thisLevelDimensions; c++) {
				int pixelWidth = (int)realWidth;
				leftoverWidth += realWidth-pixelWidth;
				if (leftoverWidth > 1) {
					pixelWidth++;
					leftoverWidth--;
				}
				int pixelHeight = (int)realHeight;
				leftoverHeight += realHeight-pixelHeight;
				if (leftoverHeight > 1) {
					pixelHeight++;
					leftoverHeight--;
				}
				drawValue(g, (int)(realWidth*c), (int)(realHeight*r), pixelWidth, pixelHeight, values[lvl][r][c]);
			}
		}
		
	}
	
	private void drawValue(Graphics g, int x, int y, int width, int height, int value) {
		if (value == X) {
			g.setColor(new Color(200,0,0,64));
		} else if (value == O) {
			g.setColor(new Color(0, 200, 0, 64));
		} else {
			g.setColor(new Color(255,255,255,64));
		}
		g.fillOval(x, y, width, height);
	}
}
